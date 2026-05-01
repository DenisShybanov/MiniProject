package com.qrmaster.data.local;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class QRCodeDao_Impl implements QRCodeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<QRCodeEntity> __insertionAdapterOfQRCodeEntity;

  private final EntityDeletionOrUpdateAdapter<QRCodeEntity> __deletionAdapterOfQRCodeEntity;

  private final EntityDeletionOrUpdateAdapter<QRCodeEntity> __updateAdapterOfQRCodeEntity;

  private final SharedSQLiteStatement __preparedStmtOfDeleteQRCodeById;

  public QRCodeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfQRCodeEntity = new EntityInsertionAdapter<QRCodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR REPLACE INTO `qr_codes` (`id`,`content`,`type`,`timestamp`,`isGenerated`,`title`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QRCodeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getContent());
        statement.bindString(3, entity.getType());
        statement.bindLong(4, entity.getTimestamp());
        final int _tmp = entity.isGenerated() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getTitle());
      }
    };
    this.__deletionAdapterOfQRCodeEntity = new EntityDeletionOrUpdateAdapter<QRCodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `qr_codes` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QRCodeEntity entity) {
        statement.bindLong(1, entity.getId());
      }
    };
    this.__updateAdapterOfQRCodeEntity = new EntityDeletionOrUpdateAdapter<QRCodeEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `qr_codes` SET `id` = ?,`content` = ?,`type` = ?,`timestamp` = ?,`isGenerated` = ?,`title` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final QRCodeEntity entity) {
        statement.bindLong(1, entity.getId());
        statement.bindString(2, entity.getContent());
        statement.bindString(3, entity.getType());
        statement.bindLong(4, entity.getTimestamp());
        final int _tmp = entity.isGenerated() ? 1 : 0;
        statement.bindLong(5, _tmp);
        statement.bindString(6, entity.getTitle());
        statement.bindLong(7, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteQRCodeById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM qr_codes WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertQRCode(final QRCodeEntity qrCode,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfQRCodeEntity.insertAndReturnId(qrCode);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQRCode(final QRCodeEntity qrCode,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfQRCodeEntity.handle(qrCode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateQRCode(final QRCodeEntity qrCode,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfQRCodeEntity.handle(qrCode);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteQRCodeById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteQRCodeById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteQRCodeById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public Flow<List<QRCodeEntity>> getAllQRCodes() {
    final String _sql = "SELECT * FROM qr_codes ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"qr_codes"}, new Callable<List<QRCodeEntity>>() {
      @Override
      @NonNull
      public List<QRCodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "isGenerated");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final List<QRCodeEntity> _result = new ArrayList<QRCodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QRCodeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsGenerated);
            _tmpIsGenerated = _tmp != 0;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new QRCodeEntity(_tmpId,_tmpContent,_tmpType,_tmpTimestamp,_tmpIsGenerated,_tmpTitle);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<QRCodeEntity>> getQRCodesByType(final String type) {
    final String _sql = "SELECT * FROM qr_codes WHERE type = ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, type);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"qr_codes"}, new Callable<List<QRCodeEntity>>() {
      @Override
      @NonNull
      public List<QRCodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "isGenerated");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final List<QRCodeEntity> _result = new ArrayList<QRCodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QRCodeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsGenerated);
            _tmpIsGenerated = _tmp != 0;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new QRCodeEntity(_tmpId,_tmpContent,_tmpType,_tmpTimestamp,_tmpIsGenerated,_tmpTitle);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Flow<List<QRCodeEntity>> searchQRCodes(final String query) {
    final String _sql = "SELECT * FROM qr_codes WHERE title LIKE '%' || ? || '%' OR content LIKE '%' || ? || '%' ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindString(_argIndex, query);
    _argIndex = 2;
    _statement.bindString(_argIndex, query);
    return CoroutinesRoom.createFlow(__db, false, new String[] {"qr_codes"}, new Callable<List<QRCodeEntity>>() {
      @Override
      @NonNull
      public List<QRCodeEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "isGenerated");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final List<QRCodeEntity> _result = new ArrayList<QRCodeEntity>(_cursor.getCount());
          while (_cursor.moveToNext()) {
            final QRCodeEntity _item;
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsGenerated);
            _tmpIsGenerated = _tmp != 0;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _item = new QRCodeEntity(_tmpId,_tmpContent,_tmpType,_tmpTimestamp,_tmpIsGenerated,_tmpTitle);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getQRCodeById(final long id, final Continuation<? super QRCodeEntity> $completion) {
    final String _sql = "SELECT * FROM qr_codes WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<QRCodeEntity>() {
      @Override
      @Nullable
      public QRCodeEntity call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final int _cursorIndexOfContent = CursorUtil.getColumnIndexOrThrow(_cursor, "content");
          final int _cursorIndexOfType = CursorUtil.getColumnIndexOrThrow(_cursor, "type");
          final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
          final int _cursorIndexOfIsGenerated = CursorUtil.getColumnIndexOrThrow(_cursor, "isGenerated");
          final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
          final QRCodeEntity _result;
          if (_cursor.moveToFirst()) {
            final long _tmpId;
            _tmpId = _cursor.getLong(_cursorIndexOfId);
            final String _tmpContent;
            _tmpContent = _cursor.getString(_cursorIndexOfContent);
            final String _tmpType;
            _tmpType = _cursor.getString(_cursorIndexOfType);
            final long _tmpTimestamp;
            _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
            final boolean _tmpIsGenerated;
            final int _tmp;
            _tmp = _cursor.getInt(_cursorIndexOfIsGenerated);
            _tmpIsGenerated = _tmp != 0;
            final String _tmpTitle;
            _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
            _result = new QRCodeEntity(_tmpId,_tmpContent,_tmpType,_tmpTimestamp,_tmpIsGenerated,_tmpTitle);
          } else {
            _result = null;
          }
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
