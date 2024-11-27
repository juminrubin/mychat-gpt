import React, { useState, useEffect } from 'react';
import { useMsal, useIsAuthenticated } from "@azure/msal-react";
import { loginRequest } from "./authConfig";
import App from './App';
import SettingsPage from './SettingsPage';

const MainComponent = () => {
    const [displaySettings, setDisplaySettings] = useState(false);
    const [settings, setSettings] = useState({});
    const { instance, accounts } = useMsal();
    const isAuthenticated = useIsAuthenticated();

    const handleLogin = () => {
        instance.loginPopup(loginRequest).catch(e => {
            console.error(e);
        });
    };

    const handleLogout = () => {
        instance.logoutPopup().catch(e => {
            console.error(e);
        });
    };

    const handleSaveSettings = (newSettings) => {
        setSettings(newSettings);
        setDisplaySettings(false);
    };

    useEffect(() => {
        if (isAuthenticated && accounts[0]) {
            // Fetch user avatar here if needed
        }
    }, [isAuthenticated, accounts]);

    return (
        <div>
            {isAuthenticated ? (
                <>
                    <div style={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px' }}>
                        <button onClick={() => setDisplaySettings(true)}>Settings</button>
                        <div style={{ display: 'flex', alignItems: 'center' }}>
                            <img
                                src={accounts[0]?.avatar