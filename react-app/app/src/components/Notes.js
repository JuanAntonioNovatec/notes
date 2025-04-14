import React, {useEffect, useState} from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import {Grid, IconButton} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';     // Importa el icono de editar
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import axios from "axios";
import Button from "@mui/material/Button";


const handleEdit = (id) => {
    console.log("Editar item con ID:", id);
};

const handleDelete = (id) => {
    console.log("Eliminar item con ID:", id);
};

const Notes = () => {

    const [items, setItems] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                console.log('get hereeee');
                const response = await axios.get('http://localhost:8080/api/notes');
                setItems(response.data);
            } catch (error) {
                console.error("Error al traer los datos: ", error);
            }
        };

        fetchData().then(r => {});
    }, []);

    const handleAddNote = () => {
        console.log("Agregar nueva nota");
        // Aquí puedes abrir un modal o un formulario para agregar una nueva nota
        // Actualiza el estado `items` para incluir la nueva nota
    };

    return (
        <div>
            <h1>Notas</h1>
            <p>Aquí puedes agregar y gestionar tus notas.</p>
            {/* Puedes agregar más contenido según sea necesario */}
            <Button
                variant="contained"
                color="primary"
                startIcon={<AddIcon />}
                onClick={handleAddNote}
                style={{ marginBottom: '16px' }} // Agrega un margen para separación
            >
                Agregar Nota
            </Button>
            <List>
                {items.map((item) => (
                    <ListItem key={item.id} divider>
                        <ListItemText primary={item.text} />
                        <Grid container spacing={1} justifyContent="flex-end">
                            <Grid item>
                                <IconButton onClick={() => handleEdit(item.id)} color="primary">
                                    <EditIcon/>
                                </IconButton>
                            </Grid>
                            <Grid item>
                                <IconButton onClick={() => handleDelete(item.id)} color="secondary">
                                    <DeleteIcon />
                                </IconButton>
                            </Grid>
                        </Grid>
                    </ListItem>
                ))}
            </List>
        </div>
    );
}

export default Notes;