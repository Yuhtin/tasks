package com.yuhtin.commissions.tasks.sql.provider.document.parser;


import com.yuhtin.commissions.tasks.sql.provider.document.Document;

public interface DocumentParser<T> {

    T parse(Document document);

}
