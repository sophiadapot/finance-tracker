package com.tracker.model;

public class User {
    private String username;
    private String password;
    private String persona; 
    
    public User(String username, String password, String persona){
        this.username = username;
        this.password = password;
        this.persona = persona; 
    }
    public String getUser(){ return username; }
    public void setUser(String username){ this.username = username;}
    public String getPassword(){  return password; }
    public void setPassword(String password){ this.password = password;}
    public String getPersona(){return persona;  }
    public void setPersona(String persona){ this.persona = persona; }
}
