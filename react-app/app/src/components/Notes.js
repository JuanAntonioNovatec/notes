import React, {useEffect, useState} from 'react';
import List from '@mui/material/List';
import ListItem from '@mui/material/ListItem';
import ListItemText from '@mui/material/ListItemText';
import {Grid, IconButton, TextField} from "@mui/material";
import EditIcon from '@mui/icons-material/Edit';     // Importa el icono de editar
import DeleteIcon from '@mui/icons-material/Delete';
import AddIcon from '@mui/icons-material/Add';
import Dialog from '@mui/material/Dialog';
import DialogActions from '@mui/material/DialogActions';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import axios from "axios";
import Button from "@mui/material/Button";
import Snackbar from '@mui/material/Snackbar'; // Importa el Snackbar
import MuiAlert from '@mui/material/Alert';


const handleEdit = (id) => {
    console.log("Editar item con ID:", id);
};

const handleDelete = (id) => {
    console.log("Eliminar item con ID:", id);
};

const Notes = () => {

    const [items, setItems] = useState([]);
    const [newNote, setNewNote] = useState('');
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [openDialog, setOpenDialog] = useState(false);
    const [noteToDelete, setNoteToDelete] = useState(null);

    const handleOpenDialog = (id) => {
        console.log('here!');
        setNoteToDelete(id);
        setOpenDialog(true);
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setNoteToDelete(null);
    };

    const fetchData = async () => {
        try {
            console.log('get hereeee');
            const response = await axios.get('http://localhost:8080/api/notes');
            setItems(response.data);
        } catch (error) {
            console.error("Error al traer los datos: ", error);
        }
    };

    useEffect(() => {
        fetchData();
    }, []);

    const handleCloseSnackbar = (event, reason) => {
        if (reason === 'clickaway') {
            return;
        }
        setOpenSnackbar(false);
    };

    const handleAddNote = async () => {
        console.log("Agregar nueva nota");
        console.log(newNote);
        const noteToAdd = {
            text: newNote
        }
        try {
        const response = await axios.post('http://localhost:8080/api/notes', noteToAdd).then(result =>
            {
                fetchData();
                setSnackbarMessage('Note saved successfuly!'); // Establecer el mensaje de éxito
                setOpenSnackbar(true); // Mostrar el Snackbar
            })
        } catch (error) {
            console.error("Something wrong saveing the note : ", error);

            // Opcional: mostrar un mensaje de error en un snackbar
            setSnackbarMessage('Something wrong saving the note.');
            setSnackbarSeverity('error'); // change the severity to 'error'
            setOpenSnackbar(true);
        }
        // Aquí puedes abrir un modal o un formulario para agregar una nueva nota
        // Actualiza el estado `items` para incluir la nueva nota
    };

    const handleDelete = async () => {
        try {
            // Realiza la solicitud DELETE a tu API
            await axios.delete(`http://localhost:8080/api/notes/${noteToDelete}`).then(result => {
                fetchData();
                setSnackbarMessage('Note deleted  successfuly!'); // Establecer el mensaje de éxito
                setOpenSnackbar(true); // Mostrar el Snackbar
            })

            // Actualiza la lista de notas después de la eliminación
            setOpenDialog(false);
            fetchData(); // Llama a fetchData para obtener la lista actualizada
        } catch (error) {
            console.error("Error al eliminar la nota: ", error);
            // Manejar error si es necesario, por ejemplo, mostrando un snackbar
            setSnackbarMessage('Error al eliminar la nota.');
            setSnackbarSeverity('error');
            setOpenSnackbar(true);
        }
    };

    return (
        <div>
            <h1>Notas</h1>
            <p>Aquí puedes agregar y gestionar tus notas.</p>
            {/* Puedes agregar más contenido según sea necesario */}
            <Grid container spacing={2} alignItems="center" style={{ marginBottom: '16px' }}>
                <Grid item xs={8}>
                    <TextField
                        fullWidth
                        variant="outlined"
                        label="Nueva Nota"
                        value={newNote}
                        onChange={(e) => setNewNote(e.target.value)}
                    />
                </Grid>
                <Grid item xs={4}>
                    <Button
                        variant="contained"
                        color="primary"
                        startIcon={<AddIcon />}
                        onClick={handleAddNote}
                    >
                        Agregar Nota
                    </Button>
                </Grid>
            </Grid>
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
                                <IconButton onClick={() => handleOpenDialog(item.id)} color="secondary">
                                    <DeleteIcon />
                                </IconButton>
                            </Grid>
                        </Grid>
                    </ListItem>
                ))}
            </List>

            <Snackbar open={openSnackbar} autoHideDuration={3000} onClose={handleCloseSnackbar}>
                <MuiAlert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{ width: '100%' }}>
                    {snackbarMessage}
                </MuiAlert>
            </Snackbar>
            <Dialog open={openDialog} onClose={handleCloseDialog}>
                <DialogTitle>Confirmar Eliminación</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        ¿Estás seguro de que deseas eliminar esta nota?
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleCloseDialog} color="primary">
                        Cancelar
                    </Button>
                    <Button onClick={handleDelete} color="secondary">
                        Eliminar
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}

export default Notes;