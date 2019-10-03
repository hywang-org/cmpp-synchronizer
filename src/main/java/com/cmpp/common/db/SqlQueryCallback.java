package com.cmpp.common.db;

import org.hibernate.Query;

/**
 * 作者： chengli
 * 日期： 2019/10/2 11:34
 */
public interface SqlQueryCallback {
    void call(Query query);
}