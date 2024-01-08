package com.HHive.hhive.domain.party.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QParty is a Querydsl query type for Party
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QParty extends EntityPathBase<Party> {

    private static final long serialVersionUID = -148480748L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QParty party = new QParty("party");

    public final com.HHive.hhive.global.auditing.QBaseTimeEntity _super = new com.HHive.hhive.global.auditing.QBaseTimeEntity(this);

    public final StringPath content = createString("content");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final com.HHive.hhive.domain.hive.entity.QHive hive;

    public final NumberPath<Long> hostId = createNumber("hostId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isDeleted = createBoolean("isDeleted");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath partyContent = createString("partyContent");

    public final StringPath partyTitle = createString("partyTitle");

    public final com.HHive.hhive.domain.user.entity.QUser user;

    public final StringPath username = createString("username");

    public QParty(String variable) {
        this(Party.class, forVariable(variable), INITS);
    }

    public QParty(Path<? extends Party> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QParty(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QParty(PathMetadata metadata, PathInits inits) {
        this(Party.class, metadata, inits);
    }

    public QParty(Class<? extends Party> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hive = inits.isInitialized("hive") ? new com.HHive.hhive.domain.hive.entity.QHive(forProperty("hive"), inits.get("hive")) : null;
        this.user = inits.isInitialized("user") ? new com.HHive.hhive.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

