package four.kjgz.logistics.mapper;

import four.kjgz.logistics.bean.Permission;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface PermissionMapper {
    @Select("select Permission.* from PermissionRole, Permission where PermissionRole.rid = #{rid} and PermissionRole.pid = Permission.id")
    public List<Permission> getPermByRid(Integer rid);
}
