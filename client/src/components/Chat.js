import React, { useState, useEffect } from "react";
import PropTypes from "prop-types";

const Chat = ({ username }) => {
    const [socket, setSocket] = useState(null);
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState("");

    useEffect(() => {
        const newSocket = new WebSocket("ws://localhost:8084/chat");
        setSocket(newSocket);

        newSocket.onopen = () => {
            console.log("WebSocket connected");
        };

        newSocket.onmessage = (event) => {
            const messageData = JSON.parse(event.data);
            setMessages((prevMessages) => [...prevMessages, messageData]);
        };

        newSocket.onclose = () => {
            console.log("WebSocket disconnected");
        };

        return () => newSocket.close();
    }, []);

    const sendMessage = () => {
        if (socket && input.trim() !== "") {
            const messageData = {
                username: username,
                message: input,
            };
            socket.send(JSON.stringify(messageData));
            setInput("");
        }
    };

    return (
        <div style={{ padding: "20px", fontFamily: "Arial, sans-serif" }}>
            <h1>Chat WebSocket</h1>
            <div
                style={{
                    border: "1px solid #ccc",
                    padding: "10px",
                    marginBottom: "10px",
                    height: "300px",
                    overflowY: "auto",
                    backgroundColor: "#f9f9f9",
                }}
            >
                {messages.map((message, index) => (
                    <div key={index}>
                        <strong>{message.username}:</strong> {message.message}
                    </div>
                ))}
            </div>
            <input
                type="text"
                placeholder="Scrie un mesaj..."
                value={input}
                onChange={(e) => setInput(e.target.value)}
                style={{ padding: "10px", width: "80%" }}
            />
            <button
                onClick={sendMessage}
                style={{
                    padding: "10px",
                    marginLeft: "10px",
                    backgroundColor: "#007BFF",
                    color: "white",
                    border: "none",
                }}
            >
                Trimite
            </button>
        </div>
    );
};

Chat.propTypes = {
    username: PropTypes.string.isRequired,
};

export default Chat;