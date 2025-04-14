import React from 'react';
import reactLogo from '../assets/React.svg';

const Home = () => {
    return (
        <div style={{
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
            height: '100vh', // Asegúrate de que el contenedor ocupe toda la altura de la ventana
            flexDirection: 'column' // Apilar el texto y el logo en columna
        }}>
            <h1>Welcome to Notes app</h1>
            <img src={reactLogo} alt="Logo de React" style={{ width: '400px', margin: '20px' }} />
            <p>Build with React and love</p>
        </div>
    );

}

export default Home;