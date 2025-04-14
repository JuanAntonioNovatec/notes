import React from 'react';
import ReactDOM from 'react-dom/client'; // Cambia esta línea si es necesario
import App from './App';
import { ThemeProvider } from '@mui/material/styles';
import theme from './theme'; // Opcional, si has creado un tema

// Crea un contenedor de raíz
const root = ReactDOM.createRoot(document.getElementById('root'));

// Renderiza la aplicación
root.render(
    <ThemeProvider theme={theme}> {/* Opcional: usa este envoltorio si implementas un tema */}
        <App />
    </ThemeProvider>
);