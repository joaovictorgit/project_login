package dadm.quixada.ufc.project_cad_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class cadastrar extends AppCompatActivity {

    Button btn_register;
    EditText n_person, email, password;
    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);


        btn_register = findViewById(R.id.registrar);
        n_person = findViewById(R.id.edtPessoa);
        email = findViewById(R.id.edtEmail);
        password = findViewById(R.id.edtSenha);

        String nome_pessoa = n_person.getText().toString();
        String _email = email.getText().toString();
        String _pass = password.getText().toString();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               autenticate(view);
            }
        });
    }

    private void autenticate(View view){
        String _email = email.getText().toString();
        String _pass = password.getText().toString();

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(_email,_pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            signup();

                            Snackbar snackbar = Snackbar.make(view, "Autenticate successful!", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }else{
                            String erro;
                            try {
                                throw task.getException();
                            }catch (FirebaseAuthWeakPasswordException e){
                                erro = "Digete uma senha com letras ou letras e números";
                            } catch (FirebaseAuthUserCollisionException e){
                                erro = "Conta já cadastrada";
                            } catch (FirebaseAuthInvalidCredentialsException e){
                                erro = "Email inválido";
                            }catch (Exception e){
                                erro = "Erro ao cadastrar";
                            }

                            Snackbar snackbar = Snackbar.make(view, erro, Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }
                });
    }

    private void signup(){
        String nome_pessoa = n_person.getText().toString();
        String _email = email.getText().toString();
        String _pass = password.getText().toString();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> user = new HashMap<>();
        user.put("nome", nome_pessoa);
        user.put("email", _email);
        user.put("senha", _pass);

        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        DocumentReference documentReference = db.collection("Pessoa").document(userID);
        documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("cadastrar", "Successful");
                Intent intent = new Intent(cadastrar.this, logar.class);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("cadastrar", "Error: " + e.toString());
                    }
                });

    }
}