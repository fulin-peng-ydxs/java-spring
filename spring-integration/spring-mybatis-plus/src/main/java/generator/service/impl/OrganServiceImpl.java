package generator.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import generator.domain.Organ;
import generator.service.OrganService;
import generator.mapper.OrganMapper;
import org.springframework.stereotype.Service;

/**
* @author PengFuLin
* @description 针对表【organ】的数据库操作Service实现
* @createDate 2023-03-13 23:17:24
*/
@Service
public class OrganServiceImpl extends ServiceImpl<OrganMapper, Organ>
    implements OrganService{

}




