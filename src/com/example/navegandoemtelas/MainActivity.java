package com.example.navegandoemtelas;

import com.example.navegandoemtelas.R.id;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
	String nome, tel, endereco;
	TextView tvNome, tvTel, tvEndereco;
	EditText etNome, etTel, etEndereco;
	SQLiteDatabase bd = null;
	Cursor cursor;
	int campoNome, campoEndereco, campoTel;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        menuPrincipal();
        abreOuCriaBanco();        
        
  }
   
    public void abreOuCriaBanco(){
    	try{
    		
    		bd = openOrCreateDatabase("android_test01", MODE_WORLD_READABLE, null);
    		String sql = "CREATE TABLE IF NOT EXISTS contatos"
    		+"(id INTEGER PRIMARY KEY, nome TEXT, endereco TEXT, telefone TEXT);";
    		
    		bd.execSQL(sql);
    		mensagemExibir("Sucesso", "Banco criado com sucesso");
    		
    	}catch(Exception erro){
    		mensagemExibir("Erro Banco", "Erro ao abrir ou criar banco de dados"+erro);
    	}
    }
    
    public void fechaBanco(){
    	try{
    		bd.close();
    	}catch(Exception erro){
    		mensagemExibir("Error", "Nao foi possivel fechar o banco");
    	}
    }
    
    public void voltar() {
		menuPrincipal();
	}

    public void chamaCadastro(){
    	setContentView(R.layout.cadastro);
    	//botao cadastrar
    	
    	Button btCadastroXml = (Button) findViewById(id.btCadastroXml);
    	btCadastroXml.setOnClickListener(new View.OnClickListener() {
    			
    			public void onClick(View v) {
    				
    				try{
    					etNome = (EditText) findViewById(R.id.etNome);
        				etTel = (EditText) findViewById(R.id.etTel);
        				etEndereco = (EditText) findViewById(R.id.etEndereco);
        				
    					String sql = "INSERT INTO contatos(nome, endereco, telefone) values('"
    					+etNome.getText().toString()+"','"
    					+etEndereco.getText().toString()+"','"
    					+etTel.getText().toString()+"')";
    					
    					bd.execSQL(sql);
    					mensagemExibir("Sucesso", "Voce cadastrou o contato com sucesso");
    				
    				}catch(Exception erro){
    					mensagemExibir("Erro", "Nao foi possivel cadastrar "+erro);
    				}
    				
    				menuPrincipal();
    			}
    		});
    	//botao voltar
    	Button btVoltar = (Button) findViewById(id.btVoltar);
    	btVoltar.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				voltar();
    			}

    		});

    }
    
    public void chamaListar(){
    	
       	if (buscarDados()){
    	setContentView(R.layout.listar);
    	mostrarDados();
    	
    	//botao pra listar o proximo elemento
    	Button btProximo = (Button) findViewById(id.btProximo);
    	btProximo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mostrarProximoRegistro();
			}
		});
    	
    	//botao pra voltar elemento no listar
    	Button btAnterior = (Button) findViewById(id.btAnterior);
    	btAnterior.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				mostrarRegistroAnterior();
				
			}
	});
    	
    	//botao voltar
    	Button btVoltar = (Button) findViewById(id.btVoltar);
    	btVoltar.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				voltar();
    			}

    		});
       	}
    	else{
    		mensagemExibir("ERROR", "Nao tem registro");
        	setContentView(R.layout.activity_main);
    	}
    }

    public void chamaAlterar(){
    	
    	if (buscarDados()){
    		setContentView(R.layout.alterar);
    		mostrarDadosAlterar();
        	
        	//botao pra listar o proximo elemento
        	Button btProximo = (Button) findViewById(id.btProximo);
        	btProximo.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				mostrarProximoRegistroAlterar();
    			}
    		});
        	
        	//botao pra voltar elemento no listar
        	Button btAnterior = (Button) findViewById(id.btAnterior);
        	btAnterior.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    			
    				mostrarRegistroAnteriorAlterar();
    				
    			}
    	});
        	
        	//botao voltar
        	Button btVoltar = (Button) findViewById(id.btVoltar);
        	btVoltar.setOnClickListener(new View.OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				voltar();
        			}

        		});
    		
    		
        	//botao pra alterar elemento
    		Button btAlterar = (Button) findViewById(id.btAlterarXml);
    		btAlterar.setOnClickListener(new View.OnClickListener() {
    				@Override
    				public void onClick(View v) {
    					
    					try{
    						
    						etNome = (EditText) findViewById(R.id.etNomeAlterar);
            				etTel = (EditText) findViewById(R.id.etTelefoneAlterar);
            				etEndereco = (EditText) findViewById(R.id.etEnderecoAlterar);
            				
        					//String sql = "UPDATE contatos SET nome ='joao' where id = 1";
            				
            				String sql = "Update contatos set nome='"+etNome.getText().toString()+"',"
        							+ "endereco ='"+etEndereco.getText().toString()+"',"
        							+ "telefone ='"+etTel.getText().toString()+"' where id="+cursor.getPosition()+01;
        					
        					bd.execSQL(sql);
        					mensagemExibir("Sucesso", "Voce alterou o contato com sucesso");
        					
    			    	}catch(Exception erro){
    			    		mensagemExibir("Erro", "Erro ao Alterar = "+erro);
    			    	}
    				}
    			});
        	
    		}
        	else{
        		mensagemExibir("ERROR", "Nao tem registro");
            	setContentView(R.layout.activity_main);
        	}
    		
}
 
    public void chamaDeletar(){
    	
    	
     	if (buscarDados()){
     		setContentView(R.layout.deletar);
        	mostrarDados();
        	
        	//botao pra listar o proximo elemento
        	Button btProximo = (Button) findViewById(id.btProximo);
        	btProximo.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    				mostrarProximoRegistro();
    			}
    		});
        	
        	//botao pra voltar elemento no listar
        	Button btAnterior = (Button) findViewById(id.btAnterior);
        	btAnterior.setOnClickListener(new View.OnClickListener() {
    			
    			@Override
    			public void onClick(View v) {
    			
    				mostrarRegistroAnterior();
    				
    			}
    	});
        	
        	//botao voltar
        	Button btVoltar = (Button) findViewById(id.btVoltar);
        	btVoltar.setOnClickListener(new View.OnClickListener() {
        			
        			@Override
        			public void onClick(View v) {
        				voltar();
        			}

        		});
        	
        	//botao deletar
        	Button btDeletar = (Button) findViewById(id.btDeletarXml);
        	btDeletar.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View arg0) {
					
					try{
        				
						String sql = "DELETE FROM contatos where id="+cursor.getPosition()+01;
    					
    					bd.execSQL(sql);
    					mensagemExibir("Sucesso", "Voce deletar o contato com sucesso");
    					
			    	}catch(Exception erro){
			    		mensagemExibir("Erro", "Erro ao Alterar = "+erro);
			    	}
				}
			});
        	
        	
           	}
        	else{
        		mensagemExibir("ERROR", "Nao tem registro");
            	setContentView(R.layout.activity_main);
        	}
 }

    public void menuPrincipal(){

    	setContentView(R.layout.activity_main);
    	
    	//chama xml Alterar
    	Button btAlterar = (Button) findViewById(id.btAlterar);
    	btAlterar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				chamaAlterar();
				
			}
		});
    	
    	//chama xml Cadastrar
    	Button btCadastrar = (Button) findViewById(id.btCadastrar);
    	btCadastrar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaCadastro();
		    	}
		});
    	
    	//chama xml Listar
    	Button btListar = (Button) findViewById(id.btListar);
    	btListar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaListar();
			}
		});
    	
    	//chama xml deletar
		Button btDeletar = (Button) findViewById(id.btDeletar);
		btDeletar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				chamaDeletar();
				
			}
		});
    }
    
    public void mensagemExibir(String titulo, String texto){
		AlertDialog.Builder mensagem = new AlertDialog.Builder(MainActivity.this);
		mensagem.setMessage(texto);
		mensagem.setTitle(titulo);
		mensagem.setNeutralButton("OK", null);
		mensagem.show();		
		
	}
  
    private boolean buscarDados(){
    	
    	try{
    		
    		cursor = bd.query("contatos", new String [] {"nome","endereco","telefone"}, null,null,null,null, null, null);
    		
    		campoNome = cursor.getColumnIndex("nome");
    		campoEndereco = cursor.getColumnIndex("endereco");
    		campoTel = cursor.getColumnIndex("telefone");
    		
    		int numeroDeRegistro = cursor.getCount();
    		if (numeroDeRegistro !=0){
    			cursor.moveToFirst();
    			return true;
    		}
    	}catch(Exception erro){
    		mensagemExibir("Erro", "Erro ao mostrar dados"+erro);
    		return false;
    	}
    	return false;
    }
    
    public void mostrarDados(){
    	
    	etNome = (EditText) findViewById(R.id.etNomeAlterar);
		etTel = (EditText) findViewById(R.id.etTelefoneAlterar);
		etEndereco = (EditText) findViewById(R.id.etEnderecoAlterar);
    	
    	tvNome = (TextView) findViewById(id.tvListarNome);
    	tvEndereco = (TextView) findViewById(id.tvListarEndereco);
    	tvTel = (TextView) findViewById(id.tvListarTelefone);    
    	
    	try{

    	tvNome.setText(cursor.getString(campoNome));
    	tvEndereco.setText(cursor.getString(campoEndereco));
    	tvTel.setText(cursor.getString(campoTel));
    	
    	etNome.setText(cursor.getString(campoNome));
    	etEndereco.setText(cursor.getString(campoEndereco));
    	etTel.setText(cursor.getString(campoTel));

    	}catch(Exception erro){
    	mensagemExibir("ERROR", "Erro no mostra dados = "+erro);
    	}
    }
    
    public void mostrarDadosAlterar(){
    	
    	etNome = (EditText) findViewById(R.id.etNomeAlterar);
		etTel = (EditText) findViewById(R.id.etTelefoneAlterar);
		etEndereco = (EditText) findViewById(R.id.etEnderecoAlterar);
    	
    	try{
    	
    	etNome.setText(cursor.getString(campoNome));
    	etEndereco.setText(cursor.getString(campoEndereco));
    	etTel.setText(cursor.getString(campoTel));

    	}catch(Exception erro){
    		mensagemExibir("ERROR", "Erro no mostra dados = "+erro);
    	}
    }
    
    public void mostrarRegistroAnterior(){
    	
    	try{
    		
    		cursor.moveToPrevious();
    		mostrarDados();
    		
    	}catch(Exception erro){
    		mensagemExibir("Error", "Voce esta no primeiro registro");
    	}
    	
    }
    
    public void mostrarProximoRegistro(){
    	
    	try{
    		
    		cursor.moveToNext();
    		mostrarDados();
    		
    	}catch(Exception erro){
    		mensagemExibir("Error", "Nao ha mais registro");
    	}
    	
    }
        

    public void mostrarRegistroAnteriorAlterar(){
    	
    	try{
    		
    		cursor.moveToPrevious();
    		mostrarDadosAlterar();
    		
    	}catch(Exception erro){
    		mensagemExibir("Error", "Voce esta no primeiro registro");
    	}
    	
    }
    
    public void mostrarProximoRegistroAlterar(){
    	
    	try{
    		
    		cursor.moveToNext();
    		mostrarDadosAlterar();
    		
    	}catch(Exception erro){
    		mensagemExibir("Error", "Nao ha mais registro");
    	}
    	
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
