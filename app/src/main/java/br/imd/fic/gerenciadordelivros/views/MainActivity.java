package br.imd.fic.gerenciadordelivros.views;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import br.imd.fic.gerenciadordelivros.R;
import br.imd.fic.gerenciadordelivros.adapter.LivroAdapter;
import br.imd.fic.gerenciadordelivros.data.LivroDAO;
import br.imd.fic.gerenciadordelivros.dialogs.DeleteDialog;
import br.imd.fic.gerenciadordelivros.dominio.Livro;

public class MainActivity extends AppCompatActivity implements LivroAdapter.OnLivroListener , DeleteDialog.OnDeleteListener {

    private LivroDAO livroDAO;
    LivroAdapter livroAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));


//      livros.add(new Livro(1L,"Android para Leigos","Michael Burton","Alta books",0));
//      livros.add(new Livro(2L,"Android para Programadores","Paul J, Deitel","Bookman",1));
//      livros.add(new Livro(3L,"Desenvolvimento para Android","Griffiths, David","Alta books",0));
//      livros.add(new Livro(4L,"Android Base de Dados","Queirós, Ricardo","FCA Editora",1));
//      livros.add(new Livro(5L,"Android em Ação","King, Chris","Elsevier - Campus",0));
//      livros.add(new Livro(6L,"Jogos em Android","Queirós, Ricardo","FCA - Editora",1));
//      livros.add(new Livro(7L,"Android Essencial com Kotlin","Ricardo R.","NOVATEC",0));

        livroDAO = LivroDAO.getInstance(this);

        List<Livro> livros = livroDAO.list();

        livroAdapter = new LivroAdapter(livros, this,this);
        recyclerView.setAdapter(livroAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.actions, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_adicionar:
                Intent intent = new Intent(getApplicationContext(),EditarLivroActivity.class);
                startActivityForResult(intent, 100);
                return true;
            case R.id.action_sair:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100 && resultCode == RESULT_OK){
            atualizaListLivros();
        }

        if(requestCode == 101 && resultCode == RESULT_OK){
            atualizaListLivros();
        }
    }

    public void atualizaListLivros(){
        List<Livro> livros = livroDAO.list();
        livroAdapter.setItens(livros);
        livroAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLivroClick(int posicao) {

        Intent intent = new Intent(getApplicationContext(),EditarLivroActivity.class);
        intent.putExtra("livro",livroAdapter.getIten(posicao));
        startActivityForResult(intent, 101);
    }

    @Override
    public void onLivroLongClick(int posicao) {

        Livro livro = livroAdapter.getIten(posicao);

        DeleteDialog dialog = new DeleteDialog();
        dialog.setLivro(livro);
        dialog.show(getSupportFragmentManager(), "deleteDialog");
    }

    @Override
    public void onDelete(Livro livro) {

        livroDAO.delete(livro);
        atualizaListLivros();

        Toast.makeText(this, "Livro excluido com sucesso!", Toast.LENGTH_SHORT).show();

    }
}