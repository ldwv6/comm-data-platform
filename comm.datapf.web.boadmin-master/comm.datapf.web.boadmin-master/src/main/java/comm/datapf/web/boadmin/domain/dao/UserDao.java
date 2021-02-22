package comm.datapf.web.boadmin.domain.dao;

import comm.datapf.web.boadmin.domain.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserDao {
    public UserDto readUser(String userName);
    public List<String> readAuthority(String username);
}
