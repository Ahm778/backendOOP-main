package com.ekher.projet.demo.utils;


import com.ekher.projet.demo.entities.Role;

public class EnumsHelperMethods {
    public static boolean isValidRole(Role role) {
        if (role == null) {
            return false;
        }
        for (Role r : Role.values()) {
            if (r == role) {
                return true;
            }
        }
        return false;
    }
}
