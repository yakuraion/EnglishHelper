package pro.yakuraion.englishhelper.data.database

import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DatabaseMigrationsTest {

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        AppDatabase::class.java
    )

    @Test
    fun migrate_1_2() {
        helper.createDatabase(TEST_DB, 1).apply {
            execSQL("INSERT INTO word (name, soundFile) VALUES ('word', 'file:///data/word.mp3')")
            close()
        }

        val db = helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2)

        val cursor = db.query(SimpleSQLiteQuery("SELECT * FROM word"))
        cursor.moveToNext()
        assertEquals("file:///data/word.mp3", cursor.getString(cursor.getColumnIndexOrThrow("soundUri")))
        assertEquals("[]", cursor.getString(cursor.getColumnIndexOrThrow("examplesJson")))
    }

    companion object {

        private const val TEST_DB = "migration-test"
    }
}
