/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

/**
 *
 * @author gustavo
 */


public class HashedPasswordGenerator {
    private String output;
    public String generateHash(String password){
        output=Hashing.sha256().hashString(password,Charsets.UTF_8).toString();
       return output;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }


}

