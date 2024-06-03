package com.example.studentmanagement;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FireBaseDatabaseHelper {
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceStudents;

    public FireBaseDatabaseHelper() {
        mDatabase = FirebaseDatabase.getInstance("https://test-firebase-e5428-default-rtdb.asia-southeast1.firebasedatabase.app/");
        mReferenceStudents = mDatabase.getReference("students");
    }

    public void addStudent(Student student) {
        String id = mReferenceStudents.push().getKey(); //Tạo một khóa duy nhất
//        student.setId(id); // Gán khóa này cho đối tượng Student
        mReferenceStudents.child(id).setValue(student);
    }

    public void updateStudent(String id, Student student) {
        mReferenceStudents.child(id).setValue(student);
    }

    public void deleteStudent(String id) {
        mReferenceStudents.child(id).removeValue();
    }

    public DatabaseReference getReference() {
        return mReferenceStudents;
    }
}
