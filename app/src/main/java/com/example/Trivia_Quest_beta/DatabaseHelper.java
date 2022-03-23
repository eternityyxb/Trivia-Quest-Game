package com.example.Trivia_Quest_beta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import androidx.annotation.Nullable;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DatabaseHelper extends SQLiteAssetHelper {
    public static final String DBNAME = "TriviaQuest.db";
    public DatabaseHelper(@Nullable Context context) {
        super(context, DBNAME, null, 1);
        //context.deleteDatabase(DBNAME); //delete this if no need update database
    }



    //register new user
    public Boolean insertData(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = myDB.insert("users", null, contentValues);

        if (result==1) return false;
        else
            return true;
    }

    public void InsertNewUser(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        myDB.execSQL("Insert into userData (username, level, currency, experience, sword_equipped, helmet_equipped, armor_equipped) values (?, '1', '0', '0', '0','0','0')",new String[]{username});

    }

    //to check username valid or not
    public Boolean checkusername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});

        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    //to check password
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public String[] GetUserData(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] data = new String[4];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM userData where username = ?", new String[]{username});

        if(cursor.moveToFirst()) {
            for(int i = 0; i < 4; i++)
                data[i] = cursor.getString(i + 1);
        }
        return data;
    }

    public String[] GetQuestions(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] data = new String[15];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Enemy where StageID = ?", new String[]{ID});

        if (cursor.moveToFirst()) {
            for(int i = 0; i < 15; i++)
                data[i] = cursor.getString(i+2); //QuestionSets
        }
        return data;
    }

    public String[] GetData(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] data = new String[7];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Questions where QuestionID = ?", new String[]{ID});

        if (cursor.moveToFirst()) {
            for(int i = 0; i < 7; i++)
                data[i] = cursor.getString(i+1); //Question, Answer, False1, False2, False3, Type, Explanation
        }
        return data;
    }

    public String[] GetUserBattleData(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] data = new String[3];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM userData where username = ?", new String[]{username});

        if(cursor.moveToFirst()) {
            data[0] = cursor.getString(2);
            data[1] = cursor.getString(3);
            data[2] = cursor.getString(4);
        }
        return data;
    }

    public String[] GetTutorial(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[] data = new String[8];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM StageTutorial where AreaID = ?", new String[]{ID});

        if (cursor.moveToFirst()) {
            for(int i = 0; i < 8; i++)
                data[i] = cursor.getString(i+2); //Title, Description
        }
        return data;
    }

    public String[][] GetStages(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[][] data = new String[4][2];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM Stage where AreaID = ?", new String[]{ID});

        if(cursor.moveToFirst()){
            for(int i = 0; i < 4; i++)
            {
                data[i][0] = cursor.getString(0);
                data[i][1] = cursor.getString(2);
                cursor.moveToNext();
            }
        }

        return data;
    }

    public void UpdateStage(String ID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("Update Stage Set Completed = 'YES' where StageID = ?", new String[]{ID});
    }

    public void UpdateUser(String username, int level, int experience, int currency){
        String lvl = String.valueOf(level);
        String exp = String.valueOf(experience);
        String cur = String.valueOf(currency);
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("Update userData Set level = ?, experience = ?, currency = ? where username = ?", new String[]{lvl, exp, cur, username});
    }

    public void EquipEquipment(String equipped, String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        if(equipped.equals("sword_equipped")) {
            MyDB.execSQL("Update userData Set sword_equipped = '1' where username = ?", new String[]{username});
        }else if(equipped.equals("armor_equipped")) {
            MyDB.execSQL("Update userData Set armor_equipped = '1' where username = ?", new String[]{username});
        }else if(equipped.equals("helmet_equipped")) {
            MyDB.execSQL("Update userData Set helmet_equipped = '1' where username = ?", new String[]{username});
        }
    }

    public void UnequipEquipment(String equipped, String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        if(equipped.equals("sword_equipped")) {
            MyDB.execSQL("Update userData Set sword_equipped = '0' where username = ?", new String[]{username});
        }else if(equipped.equals("armor_equipped")) {
            MyDB.execSQL("Update userData Set armor_equipped = '0' where username = ?", new String[]{username});
        }else if(equipped.equals("helmet_equipped")) {
            MyDB.execSQL("Update userData Set helmet_equipped = '0' where username = ?", new String[]{username});
        }

    }

    public int[] getEquipmentData(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        int[] data = new int[5];
        Cursor cursor = MyDB.rawQuery("SELECT * FROM userData where username = ?", new String[]{username});

        if(cursor.moveToFirst()) {
            for(int i = 0; i < 5; i++) {
                data[i] = cursor.getInt(i + 5);
            }
        }
        return data;
    }

    //public int[] getItemData(String username){
   //     SQLiteDatabase MyDB = this.getWritableDatabase();
   //     int[] data = new int[1];
   //     Cursor cursor = MyDB.rawQuery("SELECT potion_amount FROM userData where username = ?", new String[]{username});

   //     if(cursor.moveToFirst()) {
    //        for(int i = 0; i < 1; i++) {
    //        data[i] = cursor.getInt(i + 8);
   //         }
   //     }
   //     return data;
  //  }

    public void BoughtEquipment(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("Update userData Set bought_necklace = '1' where username = ?", new String[]{username});

    }

    public void BoughtItems(int potion_amount, String username){
        String potion = String.valueOf(potion_amount);
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("Update userData Set potion_amount = ? where username = ?",new String[]{potion,username});

    }

    public void UpdateAnswer(String QuestionID) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        MyDB.execSQL("Update Questions Set isAnswered = 'YES' where QuestionID = ?", new String[]{QuestionID});
    }

    public String[][] GetAllQuestionData(String area){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        String[][] data = new String [30][3];
        Cursor cursor = MyDB.rawQuery("Select Questions, Answer, isAnswered From Questions where Area = ?", new String[]{area});
        if(cursor.moveToFirst()){
            for(int i = 0; i < 30; i++)
            {
                data[i][0] = cursor.getString(0);
                data[i][1] = cursor.getString(1);
                data[i][2] = cursor.getString(2);

                if(data[i][2].equals("NO")) {
                    data[i][0] = "???";
                }
                cursor.moveToNext();
            }
        }
        return data;
    }
}
