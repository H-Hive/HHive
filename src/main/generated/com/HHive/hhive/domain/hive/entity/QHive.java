package com.HHive.hhive.domain.hive.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHive is a Querydsl query type for Hive
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHive extends EntityPathBase<Hive> {

    private static final long serialVersionUID = -1707180146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHive hive = new QHive("hive");

    public final com.HHive.hhive.global.auditing.QBaseTimeEntity _super = new com.HHive.hhive.global.auditing.QBaseTimeEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> creater_id = createNumber("creater_id", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath introduction = createString("introduction");

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final com.HHive.hhive.domain.user.entity.QUser user;

    public QHive(String variable) {
        this(Hive.class, forVariable(variable), INITS);
    }

    public QHive(Path<? extends Hive> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHive(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHive(PathMetadata metadata, PathInits inits) {
        this(Hive.class, metadata, inits);
    }

    public QHive(Class<? extends Hive> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.HHive.hhive.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

