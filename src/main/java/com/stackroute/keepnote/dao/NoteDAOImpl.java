package com.stackroute.keepnote.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.stackroute.keepnote.model.Note;
import org.hibernate.Transaction;
import org.hibernate.TransactionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/*
 * This class is implementing the NoteDAO interface. This class has to be annotated with @Repository
 * annotation.
 * @Repository - is an annotation that marks the specific class as a Data Access Object, thus
 * 				 clarifying it's role.
 * @Transactional - The transactional annotation itself defines the scope of a single database
 * 					transaction. The database transaction happens inside the scope of a persistence
 * 					context.
 * */

@Repository
@Transactional
public class NoteDAOImpl implements NoteDAO {


    SessionFactory sessionFactory;


    /*
     * Autowiring should be implemented for the SessionFactory.
     */
    @Autowired
    public NoteDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    /*
     * Save the note in the database(note) table.
     */

    public boolean saveNote(Note note) {
        try {
            sessionFactory.getCurrentSession().save(note);
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    /*
     * Remove the note from the database(note) table.
     */

    public boolean deleteNote(int noteId) {
        try {
            if(getNoteById(noteId)==null){
                return false;
            }
            else
                {
                Note note1=sessionFactory.getCurrentSession().get(Note.class,noteId);
                sessionFactory.getCurrentSession().delete(note1);
                return true;
            }
        } catch (Exception e) {
            return false;
        }
    }

    /*
     * retrieve all existing notes sorted by created Date in descending
     * order(showing latest note first)
     */
    public List<Note> getAllNotes() {
        try {
            List<Note> noteList = sessionFactory.getCurrentSession().createQuery("FROM Note").list();
            return noteList;
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * retrieve specific note from the database(note) table
     */
    @Override
    public Note getNoteById(int noteId) {
        return sessionFactory.getCurrentSession().get(Note.class,noteId);
    }

    /* Update existing note */

    public boolean UpdateNote(Note note) {


        try {
           sessionFactory.getCurrentSession().update(note);
            return true;
        } catch (Exception e) {
            System.out.println("------------------------------+++++++++++++++++++++++------------------"+e);
            return false;
        }
    }

}
