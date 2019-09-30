package com.stackroute.keepnote.controller;

/*
 * Annotate the class with @Controller annotation.@Controller annotation is used to mark 
 * any POJO class as a controller so that Spring can recognize this class as a Controller
 */

import com.stackroute.keepnote.dao.NoteDAO;
import com.stackroute.keepnote.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
public class NoteController {
	/*
	 * From the problem statement, we can understand that the application requires
	 * us to implement the following functionalities.
	 * 
	 * 1. display the list of existing notes from the persistence data. Each note
	 * should contain Note Id, title, content, status and created date. 
	 * 2. Add a new note which should contain the note id, title, content and status. 
	 * 3. Delete an existing note 
	 * 4. Update an existing note
	 * 
	 */


	/*
	 * Autowiring should be implemented for the NoteDAO.
	 * Create a Note object.
	 * 
	 */

	NoteDAO noteDAO;

	@Autowired
	public NoteController(NoteDAO noteDAO)
	{
		this.noteDAO=noteDAO;
	}

	Note note= new Note();

	/*
	 * Define a handler method to read the existing notes from the database and add
	 * it to the ModelMap which is an implementation of Map, used when building
	 * model data for use with views. it should map to the default URL i.e. "/index"
	 */

	@RequestMapping("/")
	public String read(ModelMap map)
	{
		List<Note> notelist= noteDAO.getAllNotes();
		map.addAttribute("list",notelist);
		return "index";
	}

	/*
	 * Define a handler method which will read the NoteTitle, NoteContent,
	 * NoteStatus from request parameters and save the note in note table in
	 * database. Please note that the CreatedAt should always be auto populated with
	 * system time and should not be accepted from the user. Also, after saving the
	 * note, it should show the same along with existing messages. Hence, reading
	 * note has to be done here again and the retrieved notes object should be sent
	 * back to the view using ModelMap This handler method should map to the URL
	 * "/add".
	 */

	@PostMapping("/add")
	public String savenote(String noteTitle,String noteContent,String noteStatus)
	{
		note.setNoteContent(noteContent);
		note.setNoteTitle(noteTitle);
		note.setNoteStatus(noteStatus);
		LocalDateTime now= LocalDateTime.now();
		note.setCreatedAt(now);

		if(noteDAO.saveNote(note)){

		System.out.println(noteTitle+" "+noteContent+" "+noteStatus);
		ModelMap map= new ModelMap();
		map.addAttribute("note",note);
		return "redirect:/";}
		else
			return "index";
	}

	/*
	 * Define a handler method which will read the NoteId from request parameters
	 * and remove an existing note by calling the deleteNote() method of the
	 * NoteRepository class.This handler method should map to the URL "/delete".
	 */

	@GetMapping("/delete")
	public String delete(int noteId)
	{
		noteDAO.deleteNote(noteId);
		return "redirect:/";
	}


	/*
	 * Define a handler method which will update the existing note. This handler
	 * method should map to the URL "/update".
	 */

	@PostMapping("/update")
	public String update(int noteId,String noteTitle,String noteContent,String noteStatus)
	{
		note.setNoteId(noteId);
		note.setNoteContent(noteContent);
		note.setNoteStatus(noteStatus);
		note.setNoteTitle(noteTitle);;
		noteDAO.UpdateNote(note);
		return "redirect:/";
	}
}
