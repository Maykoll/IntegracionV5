package com.maykoll.integracionv5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBInterface {

    public static final String TAG = "DBInterface";
    public static final int VERSION = 2;
    private final Context contexto;
    private AyudaDB ayuda;
    private SQLiteDatabase bd;
    public static final String DBNAME = "BD_Practica2";
    //BD


    //Creacion de las tablas:

    //Usuarios
    public static final String TablaUsuario = "CREATE TABLE users " +
            "(id INTEGER PRIMARY KEY," +
            "nombre TEXT not null,"+
            "email TEXT UNIQUE not null," +
            "password TEXT not null);";

    //OCRD
    public static final String TablaInterlocutor = "CREATE TABLE OCRD" +
            "(cardcode TEXT PRIMARY KEY," +
            "cardName TEXT," +
            "LicTradNum TEXT," +
            "cellular TEXT," +
            "e_mail TEXT," +
            "AddressType TEXT," +
            "id_usuario INTEGER," +
            "FOREIGN KEY (id_usuario) REFERENCES Usuarios (id));";

    //OCPR Contactos
    public static final String TablaContactos = "CREATE TABLE OCPR" +
            "(CardCode TEXT," +
            "IDContact TEXT PRIMARY KEY," +
            "FirstName TEXT," +
            "EmailContact TEXT," +
            "FOREIGN KEY (CardCode) REFERENCES OCRD (cardcode));";

    //CDR1 Direcciones
    public static final String TablaDirecciones = "CREATE TABLE CRD1" +
            "(AType TEXT," +
            "CardCode TEXT," +
            "IDAddress TEXT PRIMARY KEY," +
            "NameAddress2 TEXT," +
            "Street TEXT," +
            "City TEXT," +
            "State TEXT," +
            "Country TEXT," +
            "ZipCode TEXT," +
            "FOREIGN KEY (AType) REFERENCES OCRD (AddressType)," +
            "FOREIGN KEY (CardCode) REFERENCES OCRD (cardcode));";


    public DBInterface(Context con) {
        this.contexto = con;
        Log.w(TAG, "creando ayuda");
        ayuda = new AyudaDB(contexto);
    }

    //abrir BD
    public DBInterface abre() throws SQLException {
        Log.w(TAG, "abrimos base de datos");
        bd = ayuda.getWritableDatabase();
        return this;
    }

    // Cierra la BD
    public void cierra() {
        ayuda.close();
    }


    //Insertar usuario
    public boolean insertarUsuario(String name, String e_mail, String pass) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("nombre", name);
        initialValues.put("email", e_mail);
        initialValues.put("password", pass);
        long result = bd.insert("users", null, initialValues);
        if (result == 1) {
            return false;
        } else
            return true;
    }

    //Insertar solo Interlocutor
    public boolean interlocutor(String cardcode, String cardname, String NIF, String phone, String email, String AddressType) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("cardcode", cardcode);
        initialValues.put("cardName", cardname);
        initialValues.put("LicTradNum", NIF);
        initialValues.put("cellular", phone);
        initialValues.put("e_mail", email);
        initialValues.put("AddressType", AddressType);
        long result = bd.insert("OCRD", null, initialValues);
        if (result == 1) {
            return false;
        } else
            return true;
    }

    //InsertarContacto
    public boolean contacto(String idContact, String nameContact, String emailContact) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("IDContact", idContact);
        initialValues.put("FirstName", nameContact);
        initialValues.put("EmailContact", emailContact);
        long result = bd.insert("OCPR", null, initialValues);
        if (result == 1) {
            return false;
        } else
            return true;
    }

    //Insertar Direccion
    public boolean Direccion(String IDdireccion, String nameDireccion, String street, String city, String state, String country, String zipcode) {
        ContentValues initialValues = new ContentValues();
        initialValues.put("IDAddress", IDdireccion);
        initialValues.put("NameAddress2", nameDireccion);
        initialValues.put("Street", street);
        initialValues.put("City", city);
        initialValues.put("State", state);
        initialValues.put("Country", country);
        initialValues.put("ZipCode", zipcode);
        long result = bd.insert("CRD1", null, initialValues);
        if (result == 1) {
            return false;
        } else
            return true;
    }


    public Cursor obtenerUsuarios() {
        return bd.query("users", new String[]
                        {"id", "email", "password"},
                null, null, null, null,
                null);

    }

    public Boolean checkusername(String email) {
        SQLiteDatabase MyDB = ayuda.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email = ?", new String[]{email});
        if (cursor.getCount() > 0) {
            return true;
        } else
            return false;
    }

    public Boolean checkusernamepassword(String email, String password) {
        SQLiteDatabase MyDB = ayuda.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("select * from users where email = ? and password = ?", new String[]{email, password});
        if (cursor.getCount() > 0) {
            return true;
        } else
            return false;
    }


    public class AyudaDB extends SQLiteOpenHelper {

        public AyudaDB(Context con) {
            super(con, DBNAME, null, VERSION);
            Log.w(TAG, "constructor de ayuda");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.w(TAG, "creando la base de datos con las tablas");
                db.execSQL(TablaUsuario);
                db.execSQL(TablaInterlocutor);
                db.execSQL(TablaContactos);
                db.execSQL(TablaDirecciones);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int VersionAntigua, int VersionNueva) {
            Log.w(TAG, "Actualizando Base de datos de la versión" +
                    VersionAntigua + "A" + VersionNueva + ". Destruirá todos los datos");
            db.execSQL("DROP TABLE IF EXISTS TablaUsuario");
            db.execSQL("DROP TABLE IF EXISTS TablaInterlocutor");
            db.execSQL("DROP TABLE IF EXISTS TablaContactos");
            db.execSQL("DROP TABLE IF EXISTS TablaDirecciones");
            onCreate(db);
        }

    }

}
