package com.example.lab5var7
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

private const val DATABASE_NAME = "jokes.db"
private const val DATABASE_VERSION = 1
private const val TABLE_JOKES = "jokes"
private const val COLUMN_ID = "id"
private const val COLUMN_TYPE = "type"
private const val COLUMN_SETUP = "setup"
private const val COLUMN_PUNCHLINE = "punchline"

/**
 * SQLiteOpenHelper subclass to manage database creation and version management.
 *
 * @param context The application context.
 */
class JokeDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    /**
     * Called when the database is created for the first time.
     * @param db The database.
     */
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_JOKES (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TYPE TEXT,
                $COLUMN_SETUP TEXT,
                $COLUMN_PUNCHLINE TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    /**
     * Called when the database needs to be upgraded.
     * @param db The database.
     * @param oldVersion The old database version.
     * @param newVersion The new database version.
     */
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_JOKES")
        onCreate(db)
    }

    /**
     * Inserts a joke into the database.
     * @param joke The joke to be inserted.
     */
    fun insertJoke(joke: Joke) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TYPE, joke.type)
            put(COLUMN_SETUP, joke.setup)
            put(COLUMN_PUNCHLINE, joke.punchline)
        }
        db.insert(TABLE_JOKES, null, values)
    }
    /**
     * Retrieves all jokes from the database.
     * @return A list of all jokes.
     */
    fun getAllJokes(): List<Joke> {
        val db = readableDatabase
        val jokes = mutableListOf<Joke>()
        val cursor = db.query(TABLE_JOKES, null, null, null, null, null, null)
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val type = getString(getColumnIndexOrThrow(COLUMN_TYPE))
                val setup = getString(getColumnIndexOrThrow(COLUMN_SETUP))
                val punchline = getString(getColumnIndexOrThrow(COLUMN_PUNCHLINE))
                jokes.add(Joke(id, type, setup, punchline))
            }
        }
        cursor.close()
        return jokes
    }
    /**
     * Clears all jokes from the database.
     */
    fun clearAllJokes() {
        val db = writableDatabase
        db.delete(TABLE_JOKES, null, null)
    }
}
