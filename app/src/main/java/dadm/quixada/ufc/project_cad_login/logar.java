package dadm.quixada.ufc.project_cad_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logar extends AppCompatActivity {

    private TextView textView;
    private Button btn_logar;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        email = findViewById(R.id.edtCad);
        password = findViewById(R.id.edtSenha);

        textView = findViewById(R.id.esqueceuSenha);
        btn_logar = findViewById(R.id.logar);

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(logar.this, cadastrar.class);
                startActivity(intent);
            }
        });

        btn_logar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _email = email.getText().toString();
                String _pass = password.getText().toString();

                if(_email.equals("") || _pass.equals("")){
                    Snackbar snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                }else{
                    autenticeUser();
                }
            }
        });
    }

    private void autenticeUser(){
        String _email = email.getText().toString();
        String _pass = password.getText().toString();

        FirebaseAuth.getInstance().signInWithEmailAndPassword(_email,_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Intent intent = new Intent(logar.this, TelaPrincipal.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }
}