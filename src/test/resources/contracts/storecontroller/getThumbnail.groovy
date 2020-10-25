package contracts.storecontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'get thumbnail image'
    description 'should return status 200 and file'
    request {
        method GET()
        url($(
                consumer(regex("/api/store/.+/thumbnail")),
                producer("/api/store/group-image-filename/thumbnail")
        ))
    }
    response {
        status 200
        body(anyNonBlankString())

    }
}
