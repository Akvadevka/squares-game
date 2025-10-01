import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import GameSetup from "../pages/GameSetup/GameSetup";
import GamePage from "../pages/GamePage/GamePage";

function AppRoutes() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<GameSetup />} />
                <Route path="/game" element={<GamePage />} />
            </Routes>
        </Router>
    );
}

export default AppRoutes;