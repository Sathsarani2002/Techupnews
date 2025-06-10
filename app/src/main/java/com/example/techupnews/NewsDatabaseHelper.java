// NewsDatabaseHelper.java
package com.example.techupnews;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

public class NewsDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";
    private static final int DATABASE_VERSION = 1;

    public NewsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE news (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "category TEXT," +
                "description TEXT," +
                "imageResId INTEGER)";
        db.execSQL(CREATE_TABLE);
        insertInitialData(db);
    }

    private void insertInitialData(SQLiteDatabase db) {
        // Sports
        insertNews(db, "Sports", "Faculty of Technology Claims Victory in Men's Overall Road Race at Inter-Faculty Championship '25. The Faculty of Technology has triumphed in the highly competitive Men's Overall Road Race at the Inter-Faculty Championship '25, demonstrating outstanding performance and securing a significant win for their faculty.", R.drawable.sports1);
        insertNews(db, "Sports", "Kusal Dissanayake Recognized for 2023 Basketball Achievements by Faculty of Technology Sports Club.The Faculty of Technology, University of Colombo, extends congratulations to Kusal Dissanayake for his outstanding basketball performance in 2023, as recognized by the Official Sports Club.", R.drawable.sports2);
        insertNews(db, "Sports", "Anticipation Builds for Techno Championship 2024 Award Ceremony.Get ready to celebrate excellence! The highly anticipated Techno Championship 2024 Award Ceremony is coming soon. Prepare to witness the recognition of outstanding achievements from this year's competition. Stay tuned for more details regarding the date and venue of this special event.", R.drawable.sports3);
        insertNews(db, "Sports", "Inter-Faculty Championship '25 Nearing: Get Ready for Athletic Excitement!.The Inter-Faculty Championship '25, featuring volleyball, badminton, and more, is fast approaching. Prepare to support your faculty in this thrilling athletic event! Dates and venues will be announced soon.", R.drawable.sports4);
        insertNews(db, "Sports", "Inter-Faculty Football Championship Kicks Off May 18th.The much-anticipated Inter-Faculty Football Championship begins May 18th at 8:00 AM at the University Grounds. Come support your faculty in thrilling matches!", R.drawable.sports5);

        // Academic
        insertNews(db, "Academic", "World Environment Day 2025: Awareness Programme by Environmental Technology Society\n" +
                "\n" +
                "The Environmental Technology Society, Faculty of Technology, University of Colombo, is hosting an awareness programme for World Environment Day 2025 on June 5th at D102. The event, featuring guest speaker Nilesh Chathuranga from Eco Spindles, focuses on technological solutions to plastic pollution and promoting sustainable environmental management.", R.drawable.academic1);
        insertNews(db, "Academic", "World Environment Day 2025: Plant Hope, Grow Awareness, Build a Green Future\n" +
                "\n" +
                "Join the Environmental Technology Society in celebrating World Environment Day 2025. Let's work together for a future where nature and humanity thrive.", R.drawable.academic2);
        insertNews(db, "Academic", "Exclusive Faculty T-Shirts Available Now!\n" +
                "\n" +
                "Order your blue or black \"Baby Crocodile Material\" polo, featuring University of Colombo Faculty of Technology branding, for Rs. 1600. Contact Pasindu (076 318 4085) or Janith (074 105 2147).", R.drawable.academic3);
        insertNews(db, "Academic", "Final Exam Timetables Updated: Students Advised to Check New Schedules\n" +
                "\n" +
                "Students are hereby informed that the final exam timetables have been updated. It is crucial for all students to review the revised schedules promptly to ensure they have the most current information regarding their upcoming examinations. Please refer to official university communication channels for the updated timetables.", R.drawable.academic4);
        insertNews(db, "Academic", "Environmental Technology Society to Hold Annual General Meeting on June 2nd\n" +
                "\n" +
                "The Environmental Technology Society, Faculty of Technology, University of Colombo, will hold its Annual General Meeting for the 2025/2026 term on June 2nd, 2025. The meeting is scheduled to begin at 12:00 PM onwards and will take place at C401. This meeting is a key event for the society's members and stakeholders.", R.drawable.academic5);

        // Events
        insertNews(db, "Events", "Free Webinar on Biomimicry and Sustainability\n" +
                "\n" +
                "Join Professor Sarath Kotagama on May 26th (7-8 PM) for a free Zoom webinar on Biomimicry and Sustainability, offering an E-Certificate. Register by May 25th.", R.drawable.events1);
        insertNews(db, "Events", "Join G17 Sri Lanka: Develop skills, network, and engage in real-world SDG actions. Apply now, islandwide undergraduates!", R.drawable.events2);
        insertNews(db, "Events", "Inter-Faculty Singing Competition Finals \"Sarasi Miyasiya\" Coming Soon!\n" +
                "\n" +
                "The grand finale of the Inter-Faculty Singing Competition, \"Sarasi Miyasiya,\" is approaching, presented by the University's Art, Music, and Cultural Centres.", R.drawable.events3);
        insertNews(db, "Events", "Photography Club Exhibition Schedule", R.drawable.events4);
        insertNews(db, "Events", "Innovation Expo 2024 Guest Invite", R.drawable.events5);
    }

    private void insertNews(SQLiteDatabase db, String category, String desc, int imgResId) {
        ContentValues values = new ContentValues();
        values.put("category", category);
        values.put("description", desc);
        values.put("imageResId", imgResId);
        db.insert("news", null, values);
    }

    public ArrayList<News> getNewsByCategory(String category) {
        ArrayList<News> newsList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query("news", null, "category=?", new String[]{category},
                null, null, "id DESC");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow("description"));
            int imageResId = cursor.getInt(cursor.getColumnIndexOrThrow("imageResId"));
            newsList.add(new News(id, category, desc, imageResId));
        }
        cursor.close();
        return newsList;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS news");
        onCreate(db);
    }
}
