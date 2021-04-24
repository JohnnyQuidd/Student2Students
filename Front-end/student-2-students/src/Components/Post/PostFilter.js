import React from 'react'
import Select from 'react-select'
import '../../css/PostFilter.css'
import makeAnimated from 'react-select/animated';

function PostFilter({selectedMajor, majors, selectedMajorHandler,
        selectedTopics, topics, selectTopicHandler, fliterPosts}) {
    const animatedComponents = makeAnimated();

    return (
        <div className="post-filter">
            <div className="filter">
                <label className="filter-label"> Major </label>
                <Select
                    closeMenuOnSelect={true}
                    className="basic-single"
                    classNamePrefix="select"
                    components={animatedComponents}
                    defaultValue={selectedMajor}
                    isClearable={true}
                    isSearchable={true}
                    name="major"
                    isMulti={false}
                    options={majors}
                    onChange={selectedMajorHandler}
                    />
            </div>
            <div className="filter">
                <label className="filter-label"> Keywords </label>
                <Select
                    closeMenuOnSelect={true}
                    className="basic-single"
                    classNamePrefix="select"
                    components={animatedComponents}
                    defaultValue={selectedTopics}
                    isClearable={true}
                    isSearchable={true}
                    name="topic"
                    isMulti={true}
                    options={topics}
                    onChange={selectTopicHandler}
                    />
            </div>
            <button className="filter-button" onClick={fliterPosts}> Filter posts </button>
        </div>
    )
}

export default PostFilter
