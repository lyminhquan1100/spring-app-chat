package spring.boot.core.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

public class CoreController<DTO extends CoreDTO,
        Entity extends CoreEntity> extends ResponseController {
    @Autowired
    private CoreService<DTO, Entity> coreService;

    protected CoreService<DTO, Entity> getService() {
        return coreService;
    }

    @GetMapping("/{id}")
    public ResponseDTO getDetail(@PathVariable Long id) {
        return response(getService().getDetail(id));
    }

    @GetMapping
    public ResponseDTO search(DTO dto,
                              @PageableDefault(size = 200,
                                      sort = "id",
                                      direction = Sort.Direction.DESC)
                                      Pageable pageable) {
        return response(getService().search(dto, pageable));
    }

    @PostMapping
    public ResponseDTO create(@Valid @RequestBody DTO dto) {
        dto.setId(null);
        return response(getService().save(dto));
    }

    @PutMapping("/{id}")
    public ResponseDTO update(@PathVariable Long id, @RequestBody DTO dto) {
        dto.setId(id);
        return response(getService().save(dto));
    }

    @DeleteMapping("/{id}")
    public ResponseDTO delete(@PathVariable Long id) {
        getService().delete(id);
        return response(null);
    }

    @PatchMapping("/{id}")
    public ResponseDTO patch(@PathVariable Long id, @RequestBody Map<String, Object> dto) {
        return response(getService().save(id, dto));
    }
}
