import React from 'react';
import {BrowserRouter as Router, Route, Routes} from 'react-router-dom';
import GameSetup from "../pages/GameSetup/GameSetup";

function AppRoutes() {

    return (
        <Router>
            <Routes>
                <Route path="/" element={<GameSetup />} />
            </Routes>
        </Router>
    );
}

export default AppRoutes;