import React, { useState } from 'react';
import axios from 'axios';

const App = ({ settings }) => {
    const [messages, setMessages] = useState([]);
    const [input, setInput] = useState('');

    const sendMessage = async () => {
        const newMessage = { role: 'user', content: input };
        setMessages([...messages, newMessage]);
        setInput('');

        try {
            const response = await axios.post('/api/chat', {
                messages: [...messages, newMessage],
                ...settings
            });
            setMessages(prevMessages => [...prevMessages, response.data]);
        } catch (error) {
            console.error('Error sending message:', error);
        }
    };

    return (
        <div>
            <h1>Chat Completion</h1>
            <div>
                {messages.map((msg, index) => (
                    <p key={index}><strong>{msg.role}:</strong> {msg.content}</p>
                ))}
            </div>
            <input
                value={input}
                onChange={(e) => setInput(e.target.value)}
                onKeyPress={(e) => e.key === 'Enter' && sendMessage()}
            />
            <button onClick={sendMessage}>Send</button>
        </div>
    );
}

export default App;