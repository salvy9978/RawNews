package com.smartproductions.rawnews.util

import org.mindrot.jbcrypt.BCrypt
import java.security.MessageDigest

class Hash {


    fun hashFunction(base: String): String? {

        return bcrypt(base)
    }

    fun hashFunctionCompare(clearPass : String, hashToCompare : String) : Boolean{
        var equals = false

        equals = BCrypt.checkpw(clearPass, hashToCompare)

        return equals
    }


    fun sha256(base: String): String? {
        return try {
            val digest: MessageDigest = MessageDigest.getInstance("SHA-256")
            val hash: ByteArray = digest.digest(base.toByteArray(charset("UTF-8")))
            val hexString = StringBuffer()
            for (i in hash.indices) {
                val hex = Integer.toHexString(0xff and hash[i].toInt())
                if (hex.length == 1) hexString.append('0')
                hexString.append(hex)
            }
            hexString.toString()
        } catch (ex: Exception) {
            throw RuntimeException(ex)
        }
    }

    //bcrypt
    fun bcrypt(base: String): String?{

        val hashedString  = BCrypt.hashpw(base, BCrypt.gensalt(12))

        return hashedString
    }
    //pdkf2
}