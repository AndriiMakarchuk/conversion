package model.dao;

public class SQLQuery {
    private SQLQuery() {}

    public static final String INSERT_USER_DETAILS = "insert into " +
            "user_details (first_name, last_name, email, phone) " +
            "values (?, ?, ?, ?);";

    public static final String INSERT_USER = "insert into " +
            "user1 (login, password1, profile_id, user_details_id) " +
            "values (?, ?, ?, ?);";

    public static final String INSERT_AUDIO_WORD = "insert into " +
            "audio_word (word_string, language, extension, audio_word_blob) " +
            "values (?, ?, ?, ?);";

    public static final String INSERT_CONVERSION = "insert into " +
            "conversion_record (file_name, conversion_source_type, conversion_destination_type, created_date, is_converted, is_error, created_by, source_file_blob) " +
            "values (?, ?, ?, ?, ?, ?, ?, ?);";

    public static final String UPDATE_CONVERSION_BY_ID = "update conversion_record " +
            "set is_converted = ?, is_error = ? where id = ?;";

    public static final String UPDATE_CONVERSION_DESTINATION_BLOB_BY_ID = "update conversion_record " +
            "set destination_file_blob = ?, is_converted = true where id = ?;";

    public static final String SELECT_USER_BY_LOGIN = "select * from user1 where login = ?;";

    public static final String SELECT_USER_DETAILS_BY_ID = "select * from user_details where id = ?;";

    public static final String SELECT_PROFILE_BY_ID = "select * from profile where id = ?;";

    public static final String SELECT_PROFILE_BY_DEVELOPER_NAME = "select * from profile where developer_name = ?;";

    public static final String SELECT_USER_BY_ID = "select * from user1 where id = ?;";

    public static final String SELECT_AUDIO_WORD_BY_ID = "select * from audio_word where id = ?;";

    public static final String SELECT_AUDIO_WORD_BY_WORD_STRING = "select * from audio_word where word_string = ?;";

    public static final String SELECT_AUDIO_WORDS = "select * from audio_word order by word_string";

    public static final String SELECT_CONVERSION_BY_ID = "select * from conversion_record where id = ?;";

    public static final String SELECT_CONVERSION_BY_USER = "select * from conversion_record where created_by = ? order by id desc;";

    public static final String SELECT_AUDIO_WORDS_BY_WORD_STRING = "select * from audio_word where word_string in ?;";

    public static final String DELETE_CONVERSION_BY_ID = "delete from conversion_record where id = ?;";

    public static final String DELETE_AUDIO_WORD_BY_ID = "delete from audio_word where id = ?;";


}
