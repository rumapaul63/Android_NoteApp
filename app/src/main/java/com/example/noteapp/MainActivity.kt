package com.example.noteapp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickDeleteInterface, NoteClickInterface {
    lateinit var notesRV:RecyclerView
    lateinit var addFAB:FloatingActionButton
    lateinit var viewModel: NoteViewModel



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        notesRV=findViewById(R.id.idRVNotes)
        addFAB=findViewById(R.id.idFABAddNote)
        notesRV.layoutManager=LinearLayoutManager(this)

        val noteRVAdapter=NoteRVAdapter(this,this,this)
        notesRV.adapter=noteRVAdapter
        viewModel=ViewModelProvider(this,ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this, { list->
            list?.let{
                noteRVAdapter.updateList(it)

            }


        })
        addFAB.setOnClickListener{
            val intent=Intent(this@MainActivity,AddEditNoteActivity::class.java)
            startActivity(intent)
            this.finish()
        }


        }

    override fun OnDeleteIconClick(note: Note) {
       viewModel.deleteNote(note)
        Toast.makeText(this,"${note.noteTitle}Deleted",Toast.LENGTH_LONG).show()


    }

    override fun OnNoteClick(note: Note) {
        val intent=Intent(this@MainActivity,AddEditNoteActivity::class.java)
        intent.putExtra("NoteType","Edit")
        intent.putExtra("NoteTitle",note.noteTitle)
        intent.putExtra("NoteDescription",note.noteDescription)
        intent.putExtra("NoteID",note.id)
        startActivity(intent)
        this.finish()




    }
}