package com.example.hnl.myapplication;

import android.media.Image;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

public class SingUp extends AppCompatActivity {

    MaterialEditText edtPhone,edtuser,edtpassword;
    ImageButton btcancel;
    ImageButton btsignup;
    RadioGroup radioGroup;
    RadioButton nguoimua,nguoiban;
    Byte type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sing_up);
        edtPhone=(MaterialEditText)findViewById(R.id.edtPhone);
        edtuser=(MaterialEditText)findViewById(R.id.edtUser);
        edtpassword=(MaterialEditText)findViewById(R.id.edtPassword);
        btsignup=(ImageButton) findViewById(R.id.btnSignUp);
        btcancel=(ImageButton) findViewById(R.id.btncancel);
        radioGroup=(RadioGroup)findViewById(R.id.rdgChoseGroup);
        nguoiban=(RadioButton)findViewById(R.id.rdbSeller);
        nguoimua=(RadioButton)findViewById(R.id.rdbCustomer);
        btcancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_User = database.getReference("User");


        btsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                            table_User.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.child(edtPhone.getText().toString()).exists())
                                    {
                                        Toast.makeText(SingUp.this,"Số điện thoại đã tồn tại",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        if(edtPhone.getText().toString().length() < 10 || edtPhone.getText().toString().length() > 11)
                                        {
                                            Toast.makeText(SingUp.this,"Số điện thoại không hợp lê",Toast.LENGTH_LONG).show();

                                        }
                                        if(edtuser.getText().toString().length() < 4)
                                        {
                                            Toast.makeText(SingUp.this,"Tên đăng nhập không hợp lệ",Toast.LENGTH_LONG).show();

                                        }
                                        else
                                        {
                                            if(edtpassword.getText().toString().length() <4)
                                            {
                                                Toast.makeText(SingUp.this,"Mật khẩu không hợp lệ",Toast.LENGTH_LONG).show();

                                            }
                                            else
                                            {

                                                if(nguoimua.isChecked()==false && nguoiban.isChecked()==false)
                                                {
                                                    Toast.makeText(SingUp.this,"Vui lòng nhập đầy đủ thông tin",Toast.LENGTH_LONG).show();
                                                }
                                                else
                                                {
                                                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                        @Override
                                                        public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                            switch (checkedId)
                                                            {
                                                                case R.id.rdbCustomer:
                                                                    type=1;
                                                                    break;
                                                                case R.id.rdbSeller:
                                                                    type=2;
                                                                    break;
                                                            }
                                                        }
                                                    });
                                                    User user=new User(edtuser.getText().toString(),edtpassword.getText().toString(),type);
                                                    table_User.child(edtPhone.getText().toString()).setValue(user);
                                                    Toast.makeText(SingUp.this,"Đăng ký thành công",Toast.LENGTH_LONG).show();
                                                    finish();
                                                }



                                            }
                                        }

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });


            }
        });


    }
}
