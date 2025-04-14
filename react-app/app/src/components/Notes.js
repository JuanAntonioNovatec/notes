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


const Notes = () => {

    const [items, setItems] = useState([]);
    const [newNote, setNewNote] = useState('');
    const [openSnackbar, setOpenSnackbar] = useState(false);
    const [snackbarMessage, setSnackbarMessage] = useState('');
    const [snackbarSeverity, setSnackbarSeverity] = useState('success');
    const [openDialog, setOpenDialog] = useState(false);
    const [noteToDelete, setNoteToDelete] = useState(null);
    const [editingNoteId, setEditingNoteId] = useState(null);

    const handleOpenDialog = (id) => {

        setNoteToDelete(id);
        setOpenDialog(true);
    };

    const handleEdit = (id) => {
        // Carga la nota a editar
        const noteToEdit = items.find(item => item.id === id);
        setNewNote(noteToEdit.text); // Carga el texto en el campo de entrada
        setEditingNoteId(id); // Guarda el ID de la nota que se está editando
    };

    const handleCloseDialog = () => {
        setOpenDialog(false);
        setNoteToDelete(null);
    };

    const fetchData = async () => {
        try {
            const response = await axios.get('http://localhost:8080/api/notes');
            setItems(response.data);
        } catch (error) {
            console.error("Error fetching data: ", error);
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

    const handleAddOrUpdateNote = () => {


        //return;
        const noteToAddUpdate = {
            text: newNote
        }
        try {
            if (editingNoteId) {
                axios.put(`http://localhost:8080/api/notes/${editingNoteId}`, noteToAddUpdate).then(result => {
                    setNewNote('');
                    setEditingNoteId(null);
                    setSnackbarMessage('Note updated!!');
                    setOpenSnackbar(true);
                    fetchData();
                })
            } else {
                const response = axios.post('http://localhost:8080/api/notes', noteToAddUpdate).then(result => {
                    setOpenSnackbar(true); // Mostrar el Snackbar
                    setEditingNoteId(null);
                    setSnackbarMessage('Note created!!');
                    setOpenSnackbar(true);
                    fetchData();
                })
            }


        } catch (error) {
            setSnackbarMessage('Something wrong saving the note.');
            setSnackbarSeverity('error');
            setOpenSnackbar(true);
        }
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
            <Grid container spacing={2} alignItems="center" style={{marginBottom: '16px'}}>
                <Grid item xs={8}>
                    <TextField
                        fullWidth
                        variant="outlined"
                        label={editingNoteId ? "Update Note" : "Add Note"}
                        value={newNote}
                        onChange={(e) => setNewNote(e.target.value)}
                    />
                </Grid>
                <Grid item xs={4}>
                    {newNote.trim() ? ( // Mostrar el botón solo si hay texto
                        <Button
                            variant="contained"
                            color="primary"
                            startIcon={<AddIcon/>}
                            onClick={handleAddOrUpdateNote}
                        >
                            {editingNoteId ? "Update Note" : "Add Note"}
                        </Button>
                    ) : null} {/* Si no hay texto, no mostrar nada */}
                </Grid>
            </Grid>
            <List>
                {items.length === 0 ? (
                    <ListItem>
                        <ListItemText primary="No hay notas disponibles."/>
                    </ListItem>
                ) : (
                    items.map((item) => (
                        <ListItem key={item.id} divider>
                            <ListItemText primary={item.id + ': ' + item.text}/>
                            <Grid container spacing={1} justifyContent="flex-end">
                                <Grid item>
                                    <IconButton onClick={() => handleEdit(item.id)} color="primary">
                                        <EditIcon/>
                                    </IconButton>
                                </Grid>
                                <Grid item>
                                    <IconButton onClick={() => handleOpenDialog(item.id)} color="secondary">
                                        <DeleteIcon/>
                                    </IconButton>
                                </Grid>
                            </Grid>
                        </ListItem>
                    ))
                )}
            </List>

            <Snackbar open={openSnackbar} autoHideDuration={3000} onClose={handleCloseSnackbar}>
                <MuiAlert onClose={handleCloseSnackbar} severity={snackbarSeverity} sx={{width: '100%'}}>
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