package com.example.emergencyapp;

public class User {

    public String fullName, email, gender, blood, diseases, drugs, allergies, notes;
    public int birth, phone;

    public String getFullName() {return fullName;}
    public void setFullName(String fullName) {this.fullName = fullName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public int getPhone() {return phone;}
    public void setPhone(int phone) {this.phone = phone;}

    public String getGender() {return gender;}
    public void setGender(String gender) {this.gender = gender;}

    public String getBlood() {return blood;}
    public void setBlood(String blood) {this.blood = blood;}

    public String getDiseases() {return diseases;}
    public void setDiseases(String diseases) {this.diseases = diseases;}

    public String getDrugs() {return drugs;}
    public void setDrugs(String drugs) {this.drugs = drugs;}

    public String getAllergies() {return allergies;}
    public void setAllergies(String allergies) {this.allergies = allergies;}

    public String getNotes() {return notes;}
    public void setNotes(String notes) {this.notes = notes;}

    public int getBirth() {return birth;}
    public void setBirth(int birth) {this.birth = birth;}

    public User() {
        //
    }

    public User(String fullName, String email, String gender, String blood, String diseases, String drugs, String allergies, String notes, int birth, int phone) {
        setFullName(fullName);
        setEmail(email);
        setGender(gender);
        setBlood(blood);
        setDiseases(diseases);
        setDrugs(drugs);
        setAllergies(allergies);
        setNotes(notes);
        setBirth(birth);
        setPhone(phone);
    }

}
