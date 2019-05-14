package four.kjgz.logistics.mapper;

import four.kjgz.logistics.bean.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface MenuMapper {
    @Select("select Menu.* from RoleMenu, Menu where RoleMenu.rid = #{rid} and RoleMenu.mid = Menu.id")
    public List<Menu> findByRid(Integer rid);
}
