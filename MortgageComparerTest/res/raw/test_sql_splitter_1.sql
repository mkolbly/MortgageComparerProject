-------------------------------------------------------------------------------
-- 
-- deletion.  This is a simple way to ensure that removed data doesn't
-- reappear while waiting for the server data to get updated.  DeletedTimestamp
-- is so during program initialization this table can be trimmed.
-------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS [Foo1] (
   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
   Version INTEGER NOT NULL
);

-------------------------------------------------------------------------------
-- Table containing Id's of all deleted CallHandlingModeMessages and time of
-- deletion.  This is a simple way to ensure that removed data doesn't
-- reappear while waiting for the server data to get updated.  DeletedTimestamp
-- is so during program initialization this table can be trimmed.
-------------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS [Contacts]
(
   _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
   Id TEXT
);
