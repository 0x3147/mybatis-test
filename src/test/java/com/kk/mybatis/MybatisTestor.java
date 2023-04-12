package com.kk.mybatis;

import com.kk.mybatis.entity.Goods;
import com.kk.mybatis.utils.MybatisUtils;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MybatisTestor {

    @Test
    public void testSqlSessionFactory() throws IOException {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        System.out.println("SqlSessionFactory 加载成功");
        SqlSession sqlSession = null;
        try {
            sqlSessionFactory.openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭sqlSession
            // 如果 type=pooled 回收到连接池
            // 如果 type=unpooled 关闭连接
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    @Test
    public void testMybatisUtils() throws Exception {
        SqlSession sqlSession = null;
        try {
            sqlSession = MybatisUtils.openSession();
            Connection connection = sqlSession.getConnection();
            System.out.println(connection);
        } catch (Exception e) {
            throw e;
        } finally {
            MybatisUtils.closeSession(sqlSession);
        }
    }

    @Test
    public void testSelectAll() throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtils.openSession();
            List<Goods> list = session.selectList("goods.selectAll");
            for (Goods good : list) {
                System.out.println(good.getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MybatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectById() throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtils.openSession();
            Goods goods = session.selectOne("goods.selectById", 1603);
            System.out.println(goods.getTitle());
        } catch (Exception e) {
            throw e;
        } finally {
            MybatisUtils.closeSession(session);
        }
    }

    @Test
    public void testSelectByPriceRange() throws Exception {
        SqlSession session = null;
        try {
            session = MybatisUtils.openSession();
            Map param = new HashMap();
            param.put("min", 100);
            param.put("max", 500);
            param.put("limit", 10);
            List<Goods> list = session.selectList("goods.selectByPriceRange", param);
            for (Goods goods : list) {
                System.out.println(goods.getTitle());
            }
        } catch (Exception e) {
            throw e;
        } finally {
            MybatisUtils.closeSession(session);
        }
    }
}
