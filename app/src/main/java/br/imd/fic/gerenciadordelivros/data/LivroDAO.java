package br.imd.fic.gerenciadordelivros.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.imd.fic.gerenciadordelivros.dominio.Livro;

public class LivroDAO {

    private SQLiteDatabase db;
    private  static LivroDAO instance;

    private LivroDAO (Context context){
        DBHelper dbHelper = DBHelper.getInstance(context);
        db = dbHelper.getWritableDatabase();
    }

    public static LivroDAO getInstance(Context context){
        if(instance == null){
            instance = new LivroDAO(context);
        }

        return instance;
    }

    public List<Livro> list(){

        String[] colunns = {
                LivroContract.Columns._ID,
                LivroContract.Columns.titulo,
                LivroContract.Columns.autor,
                LivroContract.Columns.editora,
                LivroContract.Columns.emprestado
        };

        List<Livro> livros = new ArrayList<>();

        try(
            Cursor c = db.query(LivroContract.TABLE_NAME,
                    colunns,
                    null,
                    null,
                    null,
                    null,
                    LivroContract.Columns.titulo)
        ){

            if(c.moveToFirst()){
                do{
                    Livro l = LivroDAO.fronCursor(c);
                    livros.add(l);

                }while (c.moveToNext());
            }

        }

        return livros;
    }

    private static Livro fronCursor(Cursor c){
        long id = c.getLong(c.getColumnIndex(LivroContract.Columns._ID));
        String titulo = c.getString(c.getColumnIndex(LivroContract.Columns.titulo));
        String autor = c.getString(c.getColumnIndex(LivroContract.Columns.autor));
        String editora = c.getString(c.getColumnIndex(LivroContract.Columns.editora));
        int emprestado = c.getInt(c.getColumnIndex(LivroContract.Columns.emprestado));

        return new Livro(id,titulo,autor,editora,emprestado);
    }

    public void save(Livro livro){
        ContentValues values = new ContentValues();

        values.put(LivroContract.Columns.titulo,livro.getTitulo());
        values.put(LivroContract.Columns.autor,livro.getAutor());
        values.put(LivroContract.Columns.editora,livro.getEditora());
        values.put(LivroContract.Columns.emprestado,livro.getEmprestado());

        long id = db.insert(LivroContract.TABLE_NAME, null,values);
        livro.setId(id);
    }

    public void update(Livro livro){
        ContentValues values = new ContentValues();

        values.put(LivroContract.Columns.titulo,livro.getTitulo());
        values.put(LivroContract.Columns.autor,livro.getAutor());
        values.put(LivroContract.Columns.editora,livro.getEditora());
        values.put(LivroContract.Columns.emprestado,livro.getEmprestado());

        db.update(LivroContract.TABLE_NAME,
                values,
                LivroContract.Columns._ID+"=?",
                new String[]{String.valueOf(livro.getId())}
                );
    }

    public void delete(Livro livro){
        db.delete(LivroContract.TABLE_NAME,
                LivroContract.Columns._ID+"=?",
                new String[]{String.valueOf(livro.getId())}
        );
    }



}
