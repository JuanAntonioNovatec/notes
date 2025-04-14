import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'; // Importa el enrutador
import Navbar from './components/Navbar'; // Asegúrate de que este componente exista
import Home from './components/Home';
import Notes from "./components/Notes"; // Asegúrate de que este componente exista

const App = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/home" element={<Home />} />
                <Route path="/notes" element={<Notes />} />
            </Routes>
        </Router>
    );
};

export default App;