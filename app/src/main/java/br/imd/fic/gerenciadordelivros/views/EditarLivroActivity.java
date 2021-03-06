package br.imd.fic.gerenciadordelivros.views;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import br.imd.fic.gerenciadordelivros.R;
import br.imd.fic.gerenciadordelivros.data.LivroDAO;
import br.imd.fic.gerenciadordelivros.dominio.Livro;

public class EditarLivroActivity extends AppCompatActivity {

    private EditText edt_titulo;
    private EditText edt_autor;
    private EditText edt_editora;
    private CheckBox chk_emprestato;

    private LivroDAO livroDAO;

    private Livro livro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_livro);
        edt_titulo = findViewById(R.id.edt_titulo);
        edt_autor = findViewById(R.id.edt_Autor);
        edt_editora = findViewById(R.id.edt_Editora);
        chk_emprestato = findViewById(R.id.check_emprestado);

        livroDAO = LivroDAO.getInstance(this);

        livro = (Livro) getIntent().getSerializableExtra("livro");

        if(livro != null){
            edt_titulo.setText(livro.getTitulo());
            edt_autor.setText(livro.getAutor());
            edt_editora.setText(livro.getEditora());
            chk_emprestato.setChecked((livro.getEmprestado() == 1) ? true : false);
        }
    }

    public void cancelar(View view) {
        setResult(RESULT_CANCELED);
        finish();
    }

    public void processar(View view) {

        String titulo = edt_titulo.getText().toString();
        String autor = edt_autor.getText().toString();
        String editora = edt_editora.getText().toString();
        int emprestado = (chk_emprestato).isChecked() ? 1 : 0;

        String msg;
        if(livro == null) {
            Livro livro = new Livro(titulo, autor, editora, emprestado);
            livroDAO.save(livro);
            msg = "Livro Adicionado com Sucesso! ID="+livro.getId();

        }else {
            livro.setTitulo(titulo);
            livro.setAutor(autor);
            livro.setEditora(editora);
            livro.setEmprestado(emprestado);

            livroDAO.update(livro);
            msg = "Livro Atualizado com Sucesso! ID="+livro.getId();

        }

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        finish();

    }
}