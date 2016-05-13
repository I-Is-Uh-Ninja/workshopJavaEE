/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package helpers;

import com.lambdaworks.crypto.SCryptUtil;

/**
 *
 * @author Ian
 */
public class SCryptHelper {
    
    public static boolean validate(String toCheck, String encrypted){
        return SCryptUtil.check(toCheck, encrypted);
    }
    
    public static String encrypt(String toEncrypt){
        return SCryptUtil.scrypt(toEncrypt, 16, 16, 16);
    }
}
