import React, { useState, useRef, useEffect } from 'react';
import { MdSend, MdHome, MdAttachment, MdAttachFile, MdDelete } from 'react-icons/md';
import useChatContext from '../context/ChatContext';
import { useNavigate } from 'react-router';
import { baseURL } from "../config/AxiosHelper";
import { Stomp } from '@stomp/stompjs';
import toast from "react-hot-toast";
import SockJS from "sockjs-client";
import { getMessagess, deleteMessage } from '../service/RoomService';
import { timeAgo } from '../config/helper';


const ChatPage = () => {
  const {
    roomId,
    currentUser,
    connected,
    setConnected,
    setRoomId,
    setCurrentUser,
  } = useChatContext();

const navigate=useNavigate()
  useEffect(()=>{
   if(!connected){
     navigate('/')
   }
  },[
    connected,roomId,currentUser

  ])
  
  const [messages, setMessages]=useState([
    
  
  ]);
  const [input, setInput] = useState("");
  const inputRef = useRef(null);
  const chatBoxRef = useRef(null);
  const [stompClient, setStompClient] = useState(null);
  const [connectionAttempts, setConnectionAttempts] = useState(0);
  const MAX_RECONNECT_ATTEMPTS = 3;

  //page init:
  //messages ko load krenge
  useEffect(() => {
    async function loadMessages() {
      try {
        const messages = await getMessagess(roomId);
        console.log("Messages received:", messages); // Add this to inspect message structure
        setMessages(messages);
      } catch (error) {}
    }
    if (connected) {
      loadMessages();
    }
  }, []);

  //scroll

  useEffect(() => {
    if (chatBoxRef.current) {
      chatBoxRef.current.scroll({
        top: chatBoxRef.current.scrollHeight,
        behavior: "smooth",
      });
    }
  }, [messages]);

  //stompclient init and subscribe
  const connectWebSocket = () => {
    try {
      const sock = new SockJS(`${baseURL}/chat`);
      const client = Stomp.over(sock);

      client.connect(
        {},
        () => {
          setStompClient(client);
          setConnectionAttempts(0);
          toast.success("Connected to chat");

          client.subscribe(`/topic/room/${roomId}`, (message) => {
            try {
              const newMessage = JSON.parse(message.body);
              setMessages((prev) => [...prev, newMessage]);
            } catch (error) {
              console.error("Error processing message:", error);
            }
          });
        },
        (error) => {
          console.error("STOMP error:", error);
          if (connectionAttempts < MAX_RECONNECT_ATTEMPTS) {
            toast.error(`Connection failed. Retrying... (${connectionAttempts + 1}/${MAX_RECONNECT_ATTEMPTS})`);
            setConnectionAttempts(prev => prev + 1);
            setTimeout(connectWebSocket, 3000); // Retry after 3 seconds
          } else {
            toast.error("Could not establish connection. Please try again later.");
            setConnected(false);
            navigate("/");
          }
        }
      );
    } catch (error) {
      console.error("WebSocket connection error:", error);
      toast.error("Failed to connect to chat server");
    }
  };

  useEffect(() => {
    if (connected) {
      connectWebSocket();
    }
    
    return () => {
      if (stompClient) {
        stompClient.disconnect();
      }
    };
  }, [roomId])

  //send message handle

  const sendMessage=async ()=>{
  if(stompClient && connected && input.trim){
    console.log(input);
    
    const message = {
      sender:currentUser,
      content:input,
      roomId:roomId
    }

    stompClient.send(`/app/sendMessage/${roomId}`,
{},
      JSON.stringify(message),
    )
    setInput("")
  }
  }

  
  function handleLogout() {
    stompClient.disconnect();
    setConnected(false);
    setRoomId("");
    setCurrentUser("");
    navigate("/");
    toast.success("Disconnected")
  }

  const handleDeleteMessage = async (messageId) => {
    try {
      console.log("Trying to delete message with ID:", messageId);
      if (!messageId) {
        toast.error("Message ID not found");
        return;
      }
      
      await deleteMessage(roomId, messageId);
      setMessages(prevMessages => prevMessages.filter(msg => msg.messageId !== messageId));
      toast.success("Message deleted successfully");
    } catch (error) {
      console.error("Delete error:", error);
      toast.error(error.response?.data?.message || "Failed to delete message");
    }
  };

  
  return (
    <div className="">
    {/* this is a header */}
    <header className="dark:border-gray-700  fixed w-full dark:bg-gray-900 py-5 shadow flex justify-around items-center">
      {/* room name container */}
      <div>
        <h1 className="text-xl font-semibold">
          Room : <span>{roomId}</span>
        </h1>
      </div>
      {/* username container */}

      <div>
        <h1 className="text-xl font-semibold">
          User : <span>{currentUser}</span>
        </h1>
      </div>
      {/* button: leave room */}
      <div>
        <button onClick={handleLogout}
        className="dark:bg-red-500 dark:hover:bg-red-700 px-3 py-2 rounded-full">
          Leave Room
        </button>
      </div>
    </header>

<main ref={chatBoxRef} className="py-20 px-3 mx-auto w-2/3 dark:bg-slate-600 h-screen overflow-auto" >

{messages.map((message, index) => (
  <div key={index} className={`flex ${message.sender === currentUser ? "justify-end" : "justify-start"}`}>
    <div className={`my-2 ${message.sender===currentUser? "bg-yellow-600":"bg-blue-600"} p-2 max-w-xs rounded-full`}>
      <div className="flex flex-row gap-2">
        <img className="h-10 w-10" src={'https://avatar.iran.liara.run/public/3'} alt="" />
        <div className='flex flex-col gap-1'>
          <p className="text-sm font-bold">{message.sender}</p>
          <p>{message.content}</p>
          <p className='text-xs px-3'>{timeAgo(message.timeStamp)}</p>
        </div>
        {message.sender === currentUser && (
          <button 
            onClick={() => {
              console.log("Message object:", message);
              handleDeleteMessage(message.messageId)  // Changed from message.id to message.messageId
            }}
            className="ml-2 text-red-500 hover:text-red-700 focus:outline-none"
            title="Delete message"
          >
            <MdDelete size={20} />
          </button>
        )}
      </div>
    </div>
  </div>
))}
<div className="message_container">


</div>
</main>

    <div className=" fixed bottom-4 w-full h-16">
      <div className="h-full pr-10 gap-4 flex items-center justify-between rounded-full w-1/2 mx-auto  dark:bg-gray-900">
      <input
      value={input}
        onChange={(e) =>{ setInput(e.target.value)}}
        onKeyDown={(e) => {
          if (e.key === "Enter") {
            sendMessage();
          }
        }}
       type="text" placeholder="Type your message here...." className="dark:border-gray-700 dark:bg-gray-800 px-5 py-2 rounded-full h-full w-full focus:outline-none " />
     <div className="flex gap-1">
     <button className="dark:bg-purple-500  dark:hover:bg-purple-900 h-10 w-10 flex justify-center items-center
       rounded-full">
        <MdAttachFile size={20}/>
      </button>
     <button
      onClick={sendMessage}
     
     className="dark:bg-blue-500 dark:hover:bg-blue-900 h-10 w-10 flex justify-center items-center
       rounded-full">
        <MdSend size={20}/>
      </button>
     </div>
      </div>

    </div>
    </div>
  )
}

export default ChatPage