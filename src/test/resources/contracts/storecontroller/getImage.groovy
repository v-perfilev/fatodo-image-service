package contracts.storecontroller

import org.springframework.cloud.contract.spec.Contract

Contract.make {
    name 'get image'
    description 'should return status 200 and file'
    request {
        method GET()
        url($(
                consumer(regex("/api/store/.+")),
                producer("/api/store/group-test_filename")
        ))
    }
    response {
        status 200
        body(anyNonBlankString())

    }
}
