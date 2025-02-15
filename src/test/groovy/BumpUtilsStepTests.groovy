// Licensed to Elasticsearch B.V. under one or more contributor
// license agreements. See the NOTICE file distributed with
// this work for additional information regarding copyright
// ownership. Elasticsearch B.V. licenses this file to you under
// the Apache License, Version 2.0 (the "License"); you may
// not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import org.junit.Before
import org.junit.Test
import static org.junit.Assert.assertTrue

class BumpUtilsStepTests extends ApmBasePipelineTest {

  @Override
  @Before
  void setUp() throws Exception {
    super.setUp()
    script = loadScript('vars/bumpUtils.groovy')
  }

  @Test
  void test_isVersionAvailable_release() throws Exception {
    def result = script.isVersionAvailable('8.0.0')
    printCallStack()
    assertTrue(result == result)
    assertTrue(assertMethodCallContainsPattern('dockerImageExists', '8.0.0-SNAPSHOT'))
  }

  @Test
  void test_isVersionAvailable_snapshot() throws Exception {
    def result = script.isVersionAvailable('8.0.0-SNAPSHOT')
    printCallStack()
    assertTrue(result == result)
    assertTrue(assertMethodCallContainsPattern('dockerImageExists', '8.0.0-SNAPSHOT'))
  }

  @Test
  void test_createBranch() throws Exception {
    def result = script.createBranch(prefix: 'foo', suffix: 'bar')
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('sh', '-b "foo-'))
    assertTrue(assertMethodCallContainsPattern('sh', '-bar"'))
  }

  @Test
  void test_areChangesToBePushed() throws Exception {
    def result = script.areChangesToBePushed('my-branch')
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('sh', 'HEAD..my-branch'))
  }

  @Test
  void test_prepareContext() throws Exception {
    def result = script.prepareContext(org: 'my-org', repo: 'my-repo')
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('git', 'my-org/my-repo'))
  }

  @Test
  void test_prepareContext_with_credentialsId() throws Exception {
    def result = script.prepareContext(org: 'my-org', repo: 'my-repo', credentialsId: 'my-creds')
    printCallStack()
    assertTrue(assertMethodCallContainsPattern('git', 'my-org/my-repo'))
    assertTrue(assertMethodCallContainsPattern('git', 'my-creds'))
  }
}
