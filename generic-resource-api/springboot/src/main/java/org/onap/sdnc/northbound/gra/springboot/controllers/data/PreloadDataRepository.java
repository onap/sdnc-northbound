package org.onap.sdnc.northbound.gra.springboot.controllers.data;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PreloadDataRepository extends CrudRepository<PreloadData, Long> {

    List<PreloadData> findByPreloadId(String preloadId);

    List<PreloadData> findByPreloadIdAndType(String preloadId, String preloadType);

}

