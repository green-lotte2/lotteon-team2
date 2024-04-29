package kr.co.lotteon.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QBanner is a Querydsl query type for Banner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBanner extends EntityPathBase<Banner> {

    private static final long serialVersionUID = 2091033103L;

    public static final QBanner banner = new QBanner("banner");

    public final StringPath bcolor = createString("bcolor");

    public final DateTimePath<java.time.LocalDateTime> bendDate = createDateTime("bendDate", java.time.LocalDateTime.class);

    public final StringPath bfile = createString("bfile");

    public final StringPath blink = createString("blink");

    public final StringPath bloaction = createString("bloaction");

    public final StringPath bmanage = createString("bmanage");

    public final StringPath bname = createString("bname");

    public final NumberPath<Integer> bno = createNumber("bno", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> bstartDate = createDateTime("bstartDate", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> bstartTime = createDateTime("bstartTime", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> endTime = createDateTime("endTime", java.time.LocalDateTime.class);

    public QBanner(String variable) {
        super(Banner.class, forVariable(variable));
    }

    public QBanner(Path<? extends Banner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBanner(PathMetadata metadata) {
        super(Banner.class, metadata);
    }

}

