package com.phantomorion.moneymanager;

import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.firestore.FirebaseFirestore;

public class FormActivity extends AppCompatActivity {
    AutoCompleteTextView name,age,income,savings;
    Button done;
    RadioGroup genderGroup;
    String gender;
    FirebaseFirestore fdb;
    String sname,sage,ssavings,sincome,sgender;
    SharedPreferences spref;
    SharedPreferences.Editor edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_actvity);
        fdb=FirebaseFirestore.getInstance();
        spref=getApplicationContext().getSharedPreferences("fcode",MODE_PRIVATE);
        edit=spref.edit();
        name=findViewById(R.id.register_name);
        age=findViewById(R.id.register_age);
        income=findViewById(R.id.register_income);
        savings=findViewById(R.id.register_savings);
        done=findViewById(R.id.done);
        genderGroup=findViewById(R.id.radio_group_gender);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sname=name.getText().toString();
                sage=age.getText().toString();
                sincome=income.getText().toString();
                ssavings=savings.getText().toString();
                int i=genderGroup.getCheckedRadioButtonId();
                if(i==R.id.male)
                {
                    gender="Male";
                }
                else
                {
                    gender="Female";
                }
                UserInfo user=new UserInfo(sname,sage,ssavings,sincome,gender);
                if(spref.getString("fcode","123456789987654321").equals("123456789987654321")) {
                    fdb.collection("users").document("families").collection(sname).document(sname).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FormActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FormActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                else {
                    fdb.collection("users").document("families").collection(spref.getString("fcode","123456789987654321")).document(sname).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(FormActivity.this, "Welcome", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FormActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                }

            }
        });
    }
}
